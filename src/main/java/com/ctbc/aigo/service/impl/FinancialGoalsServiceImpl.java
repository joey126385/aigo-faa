package com.ctbc.aigo.service.impl;

import com.ctbc.aigo.bean.ResponseMessage;
import com.ctbc.aigo.bean.dto.AssetPlanFinancialGoalsGetResponseDataDTO;
import com.ctbc.aigo.bean.dto.RequestDTO;
import com.ctbc.aigo.bean.dto.ResponseDTO;
import com.ctbc.aigo.component.CommonApiUtil;
import com.ctbc.aigo.service.FinancialGoalsService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import static com.ctbc.aigo.module.aesencryptor.AesEncryptor.getSsoUrl;
@Service
public class FinancialGoalsServiceImpl implements FinancialGoalsService {

    final CommonApiUtil commonApiUtil;

    public FinancialGoalsServiceImpl(CommonApiUtil commonApiUtil) {
        this.commonApiUtil = commonApiUtil;
    }

    @Override
    public ResponseDTO get(RequestDTO request) throws Exception {

        //理財健診
        JsonNode jsonNodeFpHealth = commonApiUtil.fpHealthAcctRecordActionReadAPI(request);
        //保單健診
        JsonNode jsonNodePolicyHealth = commonApiUtil.policyHealthReportLogActionReadAPI(request);
        //客戶風險訊息與理財目的資訊查詢服務
        JsonNode jsonNodeRisk = commonApiUtil.daasV1FcastRiskAPI(request);

        //statusCode
        ResponseMessage responseMessage = commonApiUtil.getResponseMessage(request,
                jsonNodeFpHealth.get("code").asText(),
                jsonNodePolicyHealth.get("code").asText(),
                jsonNodeRisk.get("statusCode").asText()
        );

        //錯誤訊息
        if (!responseMessage.getCode().equals("0000")) {
            return ResponseDTO.builder()
                    .setResponse(responseMessage)
                    .build();
        }

        AssetPlanFinancialGoalsGetResponseDataDTO assetPlanFinancialGoalsGetDTO = AssetPlanFinancialGoalsGetResponseDataDTO.builder()
                .setCustSsoUrl(getSsoUrl(jsonNodeRisk.get("data").get("custId").asText(), "CSFPG100"))
                .setDatalist(jsonNodeFpHealth.get("dataList"))
                .setReferenceCode(jsonNodePolicyHealth.get("referenceCode"))
                .setLastUpdate(jsonNodePolicyHealth.get("lastUpdate"))
                .setExtPolicyCheck(jsonNodeRisk.get("data").get("nonCTBCInsAdvisInd"))
                .build();

        return ResponseDTO.builder()
                .setData(assetPlanFinancialGoalsGetDTO)
                .setResponse(responseMessage)
                .build();
    }
}
