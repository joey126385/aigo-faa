package com.ctbc.aigo.service.impl;

import com.ctbc.aigo.bean.ResponseMessage;
import com.ctbc.aigo.bean.dto.CustProfileRiskDisclosuresGetResponseResponseDataDTO;
import com.ctbc.aigo.bean.dto.RequestDTO;
import com.ctbc.aigo.bean.dto.ResponseDTO;
import com.ctbc.aigo.component.CommonApiUtil;
import com.ctbc.aigo.service.RiskDisclosuresService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import static com.ctbc.aigo.module.aesencryptor.AesEncryptor.getSsoUrl;

@Service
public class RiskDisclosuresServiceImpl implements RiskDisclosuresService {

    final CommonApiUtil commonApiUtil;

    public RiskDisclosuresServiceImpl(CommonApiUtil commonApiUtil) {
        this.commonApiUtil = commonApiUtil;
    }

    @Override
    public ResponseDTO get(RequestDTO request) throws Exception {

        JsonNode jsonNode = commonApiUtil.daasV1FcastRiskAPI(request);



        ResponseMessage responseMessage = commonApiUtil.getResponseMessage(request, jsonNode.get("statusCode").asText());


        if (!responseMessage.getCode().equals("0000")) {
            return ResponseDTO.builder()
                    .setResponse(responseMessage)
                    .build();
        }

        CustProfileRiskDisclosuresGetResponseResponseDataDTO data = CustProfileRiskDisclosuresGetResponseResponseDataDTO.builder()
                .setCustSsoUrl(getSsoUrl(jsonNode.get("data").get("custId").asText(), "CSFPG100"))
                .setIsPiP(jsonNode.get("data").get("isPiP"))
                .setIsPiC(jsonNode.get("data").get("isPiC"))
                .setPiExpireDate(jsonNode.get("data").get("piExpireDate"))
                .setIsHighAssetP(jsonNode.get("data").get("isHighAssetP"))
                .setIsHighAssetC(jsonNode.get("data").get("isHighAssetC"))
                .setHighAssetExpire(jsonNode.get("data").get("highAssetExpire"))
                .build();


        return ResponseDTO.builder()
                .setData(data)
                .setResponse(responseMessage)
                .build();
    }
}
