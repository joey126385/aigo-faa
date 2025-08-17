package com.ctbc.aigo.service.impl;

import com.ctbc.aigo.bean.dto.RequestDTO;
import com.ctbc.aigo.bean.dto.ResponseDTO;
import com.ctbc.aigo.bean.ResponseMessage;
import com.ctbc.aigo.bean.dto.BestOfferAdoptionListResponseDataDTO;
import com.ctbc.aigo.component.CommonApiUtil;
import com.ctbc.aigo.entity.DmCust360Entity;
import com.ctbc.aigo.entity.DmFaCustEntity;
import com.ctbc.aigo.entity.DmSfaLeadIdEntity;
import com.ctbc.aigo.repository.DmCust360Repository;
import com.ctbc.aigo.repository.DmFaCustRepository;
import com.ctbc.aigo.repository.DmSfaLeadIdRepository;
import com.ctbc.aigo.service.LeadsService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.ctbc.aigo.module.aesencryptor.AesEncryptor.getRefNoSsoUrl;
import static com.ctbc.aigo.module.aesencryptor.AesEncryptor.getSsoUrl;

@Service
public class LeadsServiceImpl implements LeadsService {

    final DmCust360Repository dmCust360Repository;
    final DmSfaLeadIdRepository dmSfaLeadIdRepository;
    final DmFaCustRepository dmFaCustRepository;
    final CommonApiUtil commonApiUtil;

    public LeadsServiceImpl(DmCust360Repository dmCust360Repository, DmSfaLeadIdRepository dmSfaLeadIdRepository, DmFaCustRepository dmFaCustRepository, CommonApiUtil commonApiUtil) {
        this.dmCust360Repository = dmCust360Repository;
        this.dmSfaLeadIdRepository = dmSfaLeadIdRepository;
        this.dmFaCustRepository = dmFaCustRepository;
        this.commonApiUtil = commonApiUtil;
    }

    @Override
    public ResponseDTO list(RequestDTO request) throws Exception {
        ResponseMessage responseMessage = ResponseMessage.SUCCESS;
        List<BestOfferAdoptionListResponseDataDTO> responseData = new ArrayList<>();
        List<DmFaCustEntity> dmFaCustEntity = dmFaCustRepository.findByAoEmpNo(request.getFaId());//查找理專
        List<DmSfaLeadIdEntity> dmSfaLeadIdEntityList = dmSfaLeadIdRepository.findByEmpIdOrderByEndDateDesc(request.getFaId());
        boolean isError = false;

        if (dmFaCustEntity == null || dmFaCustEntity.isEmpty()) responseMessage = ResponseMessage.DATA_SOURCE_ERROR;
        if (dmSfaLeadIdEntityList == null || dmSfaLeadIdEntityList.isEmpty())
            responseMessage = ResponseMessage.DATA_SOURCE_ERROR;

        //有客戶ID
        if (request.getCustId() != null) {
            assert dmFaCustEntity != null;
            dmFaCustEntity = dmFaCustEntity.stream().filter(entity -> entity.getCustomerId().equals(request.getCustId())).toList();
        }
        /**
         *    客戶的排序，以到期日最近的客戶優先排序，若多位客戶到期日期相同，就以待辦事項數量最多的客戶優先呈現，若仍遇有客戶條件相同，就以「緊急」  「共營」  「重要」的分類依序排序 。
         */
        List<DmSfaLeadIdEntity> distinctList = dmSfaLeadIdEntityList.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() ->
                                new TreeSet<>(Comparator.comparing(DmSfaLeadIdEntity::getCustId))),
                        ArrayList::new
                ));

        Optional<DmSfaLeadIdEntity> maxCustIdEntity = dmSfaLeadIdEntityList.stream()
                .max(Comparator.comparing(DmSfaLeadIdEntity::getCustId));

        // 取得所有不重複的 custId，並放入一個 List<String>    日期大的 客戶ID 在最前面
        List<String> distinctCustIds = dmSfaLeadIdEntityList.stream()
                .map(DmSfaLeadIdEntity::getCustId) // 轉換 Stream，只取得 custId 欄位
                .distinct()                       // 進行去重
                .collect(Collectors.toList());    // 收集成一個 List

        for (String distinctCustId : distinctCustIds) {
            DmFaCustEntity byCustomerIdAndAoEmpNo = dmFaCustEntity.stream().filter(entity -> entity.getCustomerId().equals(distinctCustId)).findFirst().orElse(null);
            if (byCustomerIdAndAoEmpNo == null) {
                continue;
            }
            BestOfferAdoptionListResponseDataDTO data = new BestOfferAdoptionListResponseDataDTO();
            data.setOrder("orddder");//
            data.setCustName(byCustomerIdAndAoEmpNo.getCustChnName());
            data.setCustId(byCustomerIdAndAoEmpNo.getCustomerId());
            data.setCustSsoUrl(getSsoUrl(byCustomerIdAndAoEmpNo.getCustomerId(), "CSFPG100"));
            List<BestOfferAdoptionListResponseDataDTO.toDoList> toDoListList = new ArrayList<>();

            List<BestOfferAdoptionListResponseDataDTO.opportunity> opportunityList = new ArrayList<>();

            assert dmSfaLeadIdEntityList != null;
            List<DmSfaLeadIdEntity> dmSfaLeadIdEntityList2 = dmSfaLeadIdEntityList.stream().filter(entity -> entity.getCustId().equals(byCustomerIdAndAoEmpNo.getCustomerId())).toList();
            for (DmSfaLeadIdEntity entity : dmSfaLeadIdEntityList2) {
                BestOfferAdoptionListResponseDataDTO.toDoList toDoList = new BestOfferAdoptionListResponseDataDTO.toDoList();
                // toDoList 待辦事項清單
                if (entity.getSalesPriorityName().contains("共營")) {
                    JsonNode jsonNode = commonApiUtil.refRefrInfoActionReadAPI(request);

                    toDoList.setToDoUrl(getRefNoSsoUrl("FaAssistant", jsonNode.get("dataList").get(0).get("refrNo").asText()));
                    toDoList.setToDoId(jsonNode.get("dataList").get(0).get("refrNo").asText());
                    toDoList.setToDoName(jsonNode.get("dataList").get(0).get("refrName").asText() + jsonNode.get("dataList").get(0).get("grpPrdName").asText());
                    toDoList.setType(entity.getSalesPriorityName());
                    toDoList.setExpirationDate(jsonNode.get("dataList").get(0).get("endDate").asText());

                } else {
                    toDoList.setToDoUrl(getSsoUrl(entity.getCustId(), "SASFA200"));
                    toDoList.setToDoId(entity.getSfaLeadId());
                    toDoList.setToDoName(entity.getLeadName());
                    toDoList.setType(entity.getSalesPriorityName());
                    toDoList.setExpirationDate(entity.getEndDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
                }


                toDoListList.add(toDoList);


                //opportunity 經營機會
                List<DmCust360Entity> dmCust360EntityList = dmCust360Repository.findByCustomerId(entity.getCustId());
                for (DmCust360Entity dmCust360Entity : dmCust360EntityList) {
                    List<String> requirements = getRequirements(dmCust360Entity);
                    BestOfferAdoptionListResponseDataDTO.opportunity opportunity = new BestOfferAdoptionListResponseDataDTO.opportunity();
                    opportunity.setDashboardSsoUrl(getSsoUrl(dmCust360Entity.getCustomerId(), "SADBD00"));
                    opportunity.setRequirements(requirements);
                    opportunityList.add(opportunity);
                }

            }


            data.setToDoList(toDoListList);
            data.setOpportunity(opportunityList);
            responseData.add(data);
        }
        if (!responseMessage.getCode().equals("0000")) {
            return ResponseDTO.builder()
                    .setResponse(responseMessage)
                    .build();
        }


        return ResponseDTO.builder()
                .setData(responseData)
                .setResponse(responseMessage)
                .build();

    }

    private static List<String> getRequirements(DmCust360Entity dmCust360Entity) {
        List<String> requirements = new ArrayList<>();
        if (dmCust360Entity.getFundingNeeds() != null) requirements.add(dmCust360Entity.getFundingNeeds());
        if (dmCust360Entity.getForiegnCurrencyNeeds() != null)
            requirements.add(dmCust360Entity.getForiegnCurrencyNeeds());
        if (dmCust360Entity.getHouseFinancingNeeds() != null)
            requirements.add(dmCust360Entity.getHouseFinancingNeeds());
        if (dmCust360Entity.getInsuranceNeeds() != null) requirements.add(dmCust360Entity.getInsuranceNeeds());
        if (dmCust360Entity.getEtfNeeds() != null) requirements.add(dmCust360Entity.getEtfNeeds());
        if (dmCust360Entity.getHealthCheckNeeds() != null) requirements.add(dmCust360Entity.getHealthCheckNeeds());
        return requirements;
    }

}
