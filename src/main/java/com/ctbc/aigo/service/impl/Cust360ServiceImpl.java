package com.ctbc.aigo.service.impl;

import com.ctbc.aigo.bean.dto.RequestDTO;
import com.ctbc.aigo.bean.dto.ResponseDTO;
import com.ctbc.aigo.bean.ResponseMessage;

import com.ctbc.aigo.bean.dto.BestOfferCust360GetResponseDataDTO;
import com.ctbc.aigo.entity.DmCust360Entity;
import com.ctbc.aigo.repository.DmCust360Repository;

import com.ctbc.aigo.repository.DmFaCustRepository;
import com.ctbc.aigo.repository.DmSfaLeadIdRepository;
import com.ctbc.aigo.service.Cust360Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.ctbc.aigo.module.aesencryptor.AesEncryptor.getSsoUrl;


@Service
public class Cust360ServiceImpl implements Cust360Service {


    final DmCust360Repository dmCust360Repository;
    final DmSfaLeadIdRepository dmSfaLeadIdRepository;
    final DmFaCustRepository dmFaCustRepository;

    @Autowired
    public Cust360ServiceImpl(DmCust360Repository dmCust360Repository, DmSfaLeadIdRepository dmSfaLeadIdRepository, DmFaCustRepository dmFaCustRepository) {
        this.dmCust360Repository = dmCust360Repository;
        this.dmSfaLeadIdRepository = dmSfaLeadIdRepository;
        this.dmFaCustRepository = dmFaCustRepository;
    }


    // TODO 取得經營機會API
    @Override
    public ResponseDTO get(RequestDTO request) throws Exception {
        ResponseMessage responseMessage = ResponseMessage.SUCCESS;

        if (request.getCustId() == null || request.getCustId().isEmpty())
            responseMessage = ResponseMessage.INSUFFICIENT_QUERY_CONDITIONS;
        if (request.getFaId() == null || request.getFaId().isEmpty())
            responseMessage = ResponseMessage.INSUFFICIENT_QUERY_CONDITIONS;

        List<DmCust360Entity> dmCust360Entity = dmCust360Repository.findByCustomerId(request.getCustId());


        List<BestOfferCust360GetResponseDataDTO.Opportunity> opportunityList = dmCust360Entity.stream()
                .map(entity -> BestOfferCust360GetResponseDataDTO.Opportunity.builder()
                        .setEtfNeeds(entity.getEtfNeeds())
                        .setEtfNeedsRemark(entity.getEtfNeedsRemark())
                        .setForeignCurrencyNeeds(entity.getForiegnCurrencyNeeds())
                        .setForeignCurrencyNeedsRemark(entity.getForiegnCurrencyNeedsRemark())
                        .setHouseFinancingNeeds(entity.getHouseFinancingNeeds())
                        .setHouseFinancingNeedsRemark(entity.getHouseFinancingNeedsRemark())
                        .setInsuranceNeeds(entity.getInsuranceNeeds())
                        .setInsuranceNeedsRemark(entity.getInsuranceNeedsRemark())
                        .setLifeStageChanges(entity.getLifeStageChanges())
                        .setLifeStageChangesRemark(entity.getLifeStageChangesRemark())
                        .setHealthCheckNeeds(entity.getHealthCheckNeeds())
                        .setHealthCheckNeedsRemark(entity.getHealthCheckNeedsRemark())
                        .setFundingNeeds(entity.getFundingNeeds())
                        .setFundingNeedsRemark(entity.getFundingNeedsRemark())
                        .build())
                .collect(Collectors.toList());

        BestOfferCust360GetResponseDataDTO responseData = BestOfferCust360GetResponseDataDTO.builder()
                .setCustId(request.getCustId())
                .setCustName("")
                .setCustSsoUrl(getSsoUrl(request.getCustId(), "CSFPG100"))
                .setOpportunity(opportunityList)
                .build();

        return ResponseDTO.builder()
                .setData(responseData)
                .setResponse(responseMessage)
                .build();
    }

}
