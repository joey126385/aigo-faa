package com.ctbc.aigo.service.impl;

import com.ctbc.aigo.bean.ResponseMessage;
import com.ctbc.aigo.bean.dto.BestOfferAdvisoryGetResponseDataDTO;
import com.ctbc.aigo.bean.dto.RequestDTO;
import com.ctbc.aigo.bean.dto.ResponseDTO;
import com.ctbc.aigo.component.CommonApiUtil;
import com.ctbc.aigo.entity.DmPcoBoAdvisoryEntity;
import com.ctbc.aigo.repository.DmFaCustRepository;
import com.ctbc.aigo.repository.DmPcoBoAdvisoryRepository;
import com.ctbc.aigo.service.AdvisoryService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.ctbc.aigo.module.aesencryptor.AesEncryptor.getSsoUrl;
@Service
public class AdvisoryServiceImpl implements AdvisoryService {

    final CommonApiUtil commonApiUtil;
    final DmPcoBoAdvisoryRepository dmPcoBoAdvisoryRepository;

    public AdvisoryServiceImpl(CommonApiUtil commonApiUtil, DmFaCustRepository dmFaCustRepository, DmPcoBoAdvisoryRepository dmPcoBoAdvisoryRepository) {
        this.commonApiUtil = commonApiUtil;
        this.dmPcoBoAdvisoryRepository = dmPcoBoAdvisoryRepository;

    }

    @Override
    public ResponseDTO get(RequestDTO request) throws Exception {
        String targetSignItem = "";
        String targetSignItemRemark = "";
        String notAssetConnect = "";
        String notAssetConnectRemark = "";

        //目標達成警示查詢
        JsonNode jsonNodeFpHealthAcctTargetSignActionRead = commonApiUtil.fpHealthAcctTargetSignActionReadAPI(request);
        //尚未連結行內資產之健診項目查詢
        JsonNode jsonNodeFpHealthNotAssetConnectActionRead = commonApiUtil.fpHealthNotAssetConnectActionReadAPI(request);

        ResponseMessage responseMessage = commonApiUtil.getResponseMessage(request,
                jsonNodeFpHealthAcctTargetSignActionRead.get("code").asText(),
                jsonNodeFpHealthNotAssetConnectActionRead.get("code").asText());

        if (!responseMessage.getCode().equals("0000")) {
            return ResponseDTO.builder()
                    .setResponse(responseMessage)
                    .build();
        }

        DmPcoBoAdvisoryEntity dmPcoBoAdvisoryEntity = dmPcoBoAdvisoryRepository.findByCustomerId(request.getCustId());


        //目標達成警示
        JsonNode dataListFpHealthAcctTargetSignActionRead = jsonNodeFpHealthAcctTargetSignActionRead.get("dataList");
        if (dataListFpHealthAcctTargetSignActionRead != null && dataListFpHealthAcctTargetSignActionRead.isArray()) {
            targetSignItem = dataListFpHealthAcctTargetSignActionRead.findValuesAsText("targetSignItem")
                    .stream()
                    .collect(Collectors.joining(","));
            targetSignItemRemark = dataListFpHealthAcctTargetSignActionRead.findValuesAsText("targetSignItemRemark")
                    .stream()
                    .collect(Collectors.joining(","));
        }

        //-尚未連結行內資產
        JsonNode dataListFpHealthNotAssetConnectActionRead = jsonNodeFpHealthNotAssetConnectActionRead.get("dataList");
        if (dataListFpHealthNotAssetConnectActionRead != null && dataListFpHealthNotAssetConnectActionRead.isArray()) {
            notAssetConnect = dataListFpHealthNotAssetConnectActionRead.findValuesAsText("notAssetConnect")
                    .stream()
                    .collect(Collectors.joining(","));
            notAssetConnectRemark = dataListFpHealthNotAssetConnectActionRead.findValuesAsText("notAssetConnectRemark")
                    .stream()
                    .collect(Collectors.joining(","));
        }

        BestOfferAdvisoryGetResponseDataDTO data = BestOfferAdvisoryGetResponseDataDTO.builder()
                .setCustSsoUrl(getSsoUrl(request.getCustId(), "CSFPG100"))
                .setHealthCheckUpdateReminder(dmPcoBoAdvisoryEntity.getHealthCheckUpdateReminder())
                .setHealthCheckUpdateReminderRemark(dmPcoBoAdvisoryEntity.getHealthCheckUpdateReminderRemark())
                .setFinancialHealthCheckPending(dmPcoBoAdvisoryEntity.getFinancialHealthCheckPending())
                .setFinancialHealthCheckPendingRemark(dmPcoBoAdvisoryEntity.getFinancialHealthCheckPendingRemark())
                .setNotAssetConnect(notAssetConnect)
                .setNotAssetConnectRemark(notAssetConnectRemark)
                .setTargetSignItem(targetSignItem)
                .setTargetSignItemRemark(targetSignItemRemark)
                .build();

        return ResponseDTO.builder()
                .setData(data)
                .setResponse(responseMessage)
                .build();
    }
}
