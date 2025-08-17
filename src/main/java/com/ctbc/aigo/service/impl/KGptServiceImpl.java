//package com.ctbc.aigo.service.impl;
//
//import com.ctbc.aigo.bean.ResponseMessage;
//import com.ctbc.aigo.bean.dto.*;
//import com.ctbc.aigo.entity.DmFaCustEntity;
//import com.ctbc.aigo.entity.DmPcoBoAdvisoryEntity;
//import com.ctbc.aigo.entity.DmPcoBoRightsEntity;
//import com.ctbc.aigo.repository.DmFaCustRepository;
//import com.ctbc.aigo.repository.DmPcoBoAdvisoryRepository;
//import com.ctbc.aigo.repository.DmPcoBoRightsRepository;
//import com.ctbc.aigo.service.KGptService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static com.ctbc.aigo.module.aesencryptor.AesEncryptor.getSsoUrl;
//
//
////@Service
//public class KGptServiceImpl implements KGptService {
//    private static Logger logger = LoggerFactory.getLogger(KGptServiceImpl.class);
//
//
//    private static final ResourceBundle config = ResourceBundle.getBundle("KGpt");
//    private static final String url = config.getString("KGpt.base-url");
//
//
//    final DmFaCustRepository dmFaCustRepository;
//    final DmPcoBoRightsRepository dmPcoBoRightsRepository;
//    final DmPcoBoAdvisoryRepository dmPcoBoAdvisoryRepository;
//
//    public KGptServiceImpl(DmFaCustRepository dmFaCustRepository, DmPcoBoRightsRepository dmPcoBoRightsRepository, DmPcoBoAdvisoryRepository dmPcoBoAdvisoryRepository) {
//        this.dmFaCustRepository = dmFaCustRepository;
//        this.dmPcoBoRightsRepository = dmPcoBoRightsRepository;
//        this.dmPcoBoAdvisoryRepository = dmPcoBoAdvisoryRepository;
//    }
//
//
//    @Override
//    public ResponseDTO custProfileMemberBenefitsGetService(RequestDTO request) throws Exception {
//
//        //客戶權益優惠的資訊查詢服務
//        JsonNode jsonNodeUserProfile = daasV1FcastRiskAPI(request);
//
//        //WM001  財管權益次數查詢
//        JsonNode jsonQueryRewardQry = caip3WmRewardQryAPI(request);
//
//        //RW100a 歸戶查詢信用卡權益次數 QueryRewardById
//        JsonNode jsonQueryRewardCountById = inqueryQueryRewardCountByIdAPI(request);
//
//        //信用卡年累積消費
//        int last12MonthProcessTotal = 0;
//        String[] offerInfos = {"airportPark", "vipRoom", "vipCal", "airportPark", "parking", "roadSideRescue"};
//        for (String offerInfo : offerInfos) {
//            last12MonthProcessTotal += jsonQueryRewardCountById.get("offerInfo").get(offerInfo).get("leftTimes").asInt();
//        }
//
//        ResponseMessage responseMessage = getResponseMessage(
//                request,
//                jsonNodeUserProfile.get("statusCode").asText(),
//                jsonQueryRewardQry.get("Reshdr").get("ResultCode").asText(),
//                jsonQueryRewardCountById.get("status").asText()
//        );
//
//        if (!responseMessage.getCode().equals("0000")) {
//            return ResponseDTO.builder()
//                    .setResponse(responseMessage)
//                    .build();
//        }
//
//        //ResponseData
//        CustProfileMemberBenefitsGetResponseDataDTO data = CustProfileMemberBenefitsGetResponseDataDTO.builder()
//                .setCustSsoUrl(getSsoUrl(jsonNodeUserProfile.get("data").get("custId").asText(), "CSFPG100"))
//                .setVipSegmentHist(jsonNodeUserProfile.get("data").get("vipSegmentHist"))
//                .setVipDegree(jsonNodeUserProfile.get("data").get("vipDegree"))
//                .setFamilyVipDegree(jsonNodeUserProfile.get("data").get("familyVipDegree"))
//                .setIdvTagDate(jsonNodeUserProfile.get("data").get("idvTagDate"))
//                .setFamilyNextReviewDate(jsonNodeUserProfile.get("data").get("familyNextReviewDate"))
//                .setAumWgItems(jsonNodeUserProfile.get("data").get("aumWgItems"))
//                .setAlumItems(jsonNodeUserProfile.get("data").get("alumItems"))
//                .setAumWhItems(jsonNodeUserProfile.get("data").get("aumWhItems"))
//                .setPriorityLevel(jsonNodeUserProfile.get("data").get("priorityLevel"))
//                .setSignedRelatives(jsonNodeUserProfile.get("data").get("signedRelatives"))
//                .setWealthPointThisYear(jsonNodeUserProfile.get("data").get("wealthPointThisYear"))
//                .setWealthPointNextYear(jsonNodeUserProfile.get("data").get("wealthPointNextYear"))
//                .setWealthPointExpire(jsonNodeUserProfile.get("data").get("wealthPointExpire"))
//                .setCreditCardCount(jsonNodeUserProfile.get("data").get("creditCardCount"))
//                .setCreditCardLimitAmt(jsonNodeUserProfile.get("data").get("creditCardLimitAmt"))
//                .setCreditcardReward(jsonNodeUserProfile.get("data").get("creditcardReward"))
//                .setRewardResult(jsonQueryRewardQry.get("Resbdy").get("RewardResult"))
//                .setLast12MonthProcess(last12MonthProcessTotal + "")
//                .setAirportTrans(jsonQueryRewardCountById.get("offerInfo").get("airportPark").get("leftTimes"))
//                .setVipRoom(jsonQueryRewardCountById.get("offerInfo").get("vipRoom").get("leftTimes"))
//                .setVipCal(jsonQueryRewardCountById.get("offerInfo").get("vipCal").get("leftTimes"))
//                .setAirportPark(jsonQueryRewardCountById.get("offerInfo").get("airportPark").get("leftTimes"))
//                .setParking(jsonQueryRewardCountById.get("offerInfo").get("parking").get("leftTimes"))
//                .setRoadSideRescue(jsonQueryRewardCountById.get("offerInfo").get("roadSideRescue").get("leftTimes"))
//                .build();
//        return ResponseDTO.builder()
//                .setData(data)
//                .setResponse(responseMessage)
//                .build();
//    }
//
//    @Override
//    public ResponseDTO custProfileRiskDisclosuresGetService(RequestDTO request) throws Exception {
//        String uri = "/daas/v1/fcast/risk";
//
//
//        JsonNode jsonNode = daasV1FcastRiskAPI(request);
//
//
//
//        ResponseMessage responseMessage = getResponseMessage(request, jsonNode.get("statusCode").asText());
//
//
//        if (!responseMessage.getCode().equals("0000")) {
//            return ResponseDTO.builder()
//                    .setResponse(responseMessage)
//                    .build();
//        }
//
//        CustProfileRiskDisclosuresGetResponseResponseDataDTO data = CustProfileRiskDisclosuresGetResponseResponseDataDTO.builder()
//                .setCustSsoUrl(getSsoUrl(jsonNode.get("data").get("custId").asText(), "CSFPG100"))
//                .setIsPiP(jsonNode.get("data").get("isPiP"))
//                .setIsPiC(jsonNode.get("data").get("isPiC"))
//                .setPiExpireDate(jsonNode.get("data").get("piExpireDate"))
//                .setIsHighAssetP(jsonNode.get("data").get("isHighAssetP"))
//                .setIsHighAssetC(jsonNode.get("data").get("isHighAssetC"))
//                .setHighAssetExpire(jsonNode.get("data").get("highAssetExpire"))
//                .build();
//
//
//        return ResponseDTO.builder()
//                .setData(data)
//                .setResponse(responseMessage)
//                .build();
//    }
//
//    @Override
//    public ResponseDTO assetPlanFinancialGoalsGetService(RequestDTO request) throws Exception {
//
//        //理財健診
//        JsonNode jsonNodeFpHealth = fpHealthAcctRecordActionReadAPI(request);
//        //保單健診
//        JsonNode jsonNodePolicyHealth = policyHealthReportLogActionReadAPI(request);
//        //客戶風險訊息與理財目的資訊查詢服務
//        JsonNode jsonNodeRisk = daasV1FcastRiskAPI(request);
//
//        //statusCode
//        ResponseMessage responseMessage = getResponseMessage(request,
//                jsonNodeFpHealth.get("code").asText(),
//                jsonNodePolicyHealth.get("code").asText(),
//                jsonNodeRisk.get("statusCode").asText()
//        );
//
//        //錯誤訊息
//        if (!responseMessage.getCode().equals("0000")) {
//            return ResponseDTO.builder()
//                    .setResponse(responseMessage)
//                    .build();
//        }
//
//        AssetPlanFinancialGoalsGetResponseDataDTO assetPlanFinancialGoalsGetDTO = AssetPlanFinancialGoalsGetResponseDataDTO.builder()
//                .setCustSsoUrl(getSsoUrl(jsonNodeRisk.get("data").get("custId").asText(), "CSFPG100"))
//                .setDatalist(jsonNodeFpHealth.get("dataList"))
//                .setReferenceCode(jsonNodePolicyHealth.get("referenceCode"))
//                .setLastUpdate(jsonNodePolicyHealth.get("lastUpdate"))
//                .setExtPolicyCheck(jsonNodeRisk.get("data").get("nonCTBCInsAdvisInd"))
//                .build();
//
//        return ResponseDTO.builder()
//                .setData(assetPlanFinancialGoalsGetDTO)
//                .setResponse(responseMessage)
//                .build();
//    }
//
//    // TODO 權益通知API
//    @Override
//    public ResponseDTO bestOfferBenefitNotificationsGetService(RequestDTO request) throws Exception {
//
//        //check statusCode  return ResponseMessage
//        ResponseMessage responseMessage = getResponseMessage(request);
//        if (!responseMessage.getCode().equals("0000")) {
//            return ResponseDTO.builder()
//                    .setResponse(responseMessage)
//                    .build();
//        }
//
//        //權益通知VIEW
//        DmFaCustEntity customerIdAndAoEmpNo = (DmFaCustEntity) dmFaCustRepository.findByCustomerIdAndAoEmpNo(request.getCustId(), request.getFaId());
//        DmPcoBoRightsEntity dmPcoBoRightsEntity = dmPcoBoRightsRepository.findByCustomerId(request.getCustId());
//
//        BestOfferBenefitNotificationsGetResponseDataDTO data = BestOfferBenefitNotificationsGetResponseDataDTO.builder()
//                .setCustSsoUrl(getSsoUrl(request.getCustId(), "CSFPG100"))
//                .setMemberExpired(dmPcoBoRightsEntity.getMemberExpired())
//                .setMemberExpiredRemark(dmPcoBoRightsEntity.getMemberExpiredRemark())
//                .setTradingQualificationExpired(dmPcoBoRightsEntity.getQualificationExpired())
//                .setTradingQualificationExpiredRemark(dmPcoBoRightsEntity.getQualificationExpiredRemark())
//                .build();
//
//        return ResponseDTO.builder()
//                .setData(data)
//                .setResponse(responseMessage)
//                .build();
//    }
//
//
//    /**
//     * TODO　API名稱：Advisory API
//     */
//    @Override
//    public ResponseDTO bestOfferAdvisoryGetService(RequestDTO request) throws Exception {
//        String targetSignItem = "";
//        String targetSignItemRemark = "";
//        String notAssetConnect = "";
//        String notAssetConnectRemark = "";
//
//        //目標達成警示查詢
//        JsonNode jsonNodeFpHealthAcctTargetSignActionRead = fpHealthAcctTargetSignActionReadAPI(request);
//        //尚未連結行內資產之健診項目查詢
//        JsonNode jsonNodeFpHealthNotAssetConnectActionRead = fpHealthNotAssetConnectActionReadAPI(request);
//
//        ResponseMessage responseMessage = getResponseMessage(request,
//                jsonNodeFpHealthAcctTargetSignActionRead.get("code").asText(),
//                jsonNodeFpHealthNotAssetConnectActionRead.get("code").asText());
//
//        if (!responseMessage.getCode().equals("0000")) {
//            return ResponseDTO.builder()
//                    .setResponse(responseMessage)
//                    .build();
//        }
//
//        DmPcoBoAdvisoryEntity dmPcoBoAdvisoryEntity = dmPcoBoAdvisoryRepository.findByCustomerId(request.getCustId());
//
//
//        //目標達成警示
//        JsonNode dataListFpHealthAcctTargetSignActionRead = jsonNodeFpHealthAcctTargetSignActionRead.get("dataList");
//        if (dataListFpHealthAcctTargetSignActionRead != null && dataListFpHealthAcctTargetSignActionRead.isArray()) {
//            targetSignItem = dataListFpHealthAcctTargetSignActionRead.findValuesAsText("targetSignItem")
//                    .stream()
//                    .collect(Collectors.joining(","));
//            targetSignItemRemark = dataListFpHealthAcctTargetSignActionRead.findValuesAsText("targetSignItemRemark")
//                    .stream()
//                    .collect(Collectors.joining(","));
//        }
//
//        //-尚未連結行內資產
//        JsonNode dataListFpHealthNotAssetConnectActionRead = jsonNodeFpHealthNotAssetConnectActionRead.get("dataList");
//        if (dataListFpHealthNotAssetConnectActionRead != null && dataListFpHealthNotAssetConnectActionRead.isArray()) {
//            notAssetConnect = dataListFpHealthNotAssetConnectActionRead.findValuesAsText("notAssetConnect")
//                    .stream()
//                    .collect(Collectors.joining(","));
//            notAssetConnectRemark = dataListFpHealthNotAssetConnectActionRead.findValuesAsText("notAssetConnectRemark")
//                    .stream()
//                    .collect(Collectors.joining(","));
//        }
//
//        BestOfferAdvisoryGetResponseDataDTO data = BestOfferAdvisoryGetResponseDataDTO.builder()
//                .setCustSsoUrl(getSsoUrl(request.getCustId(), "CSFPG100"))
//                .setHealthCheckUpdateReminder(dmPcoBoAdvisoryEntity.getHealthCheckUpdateReminder())
//                .setHealthCheckUpdateReminderRemark(dmPcoBoAdvisoryEntity.getHealthCheckUpdateReminderRemark())
//                .setFinancialHealthCheckPending(dmPcoBoAdvisoryEntity.getFinancialHealthCheckPending())
//                .setFinancialHealthCheckPendingRemark(dmPcoBoAdvisoryEntity.getFinancialHealthCheckPendingRemark())
//                .setNotAssetConnect(notAssetConnect)
//                .setNotAssetConnectRemark(notAssetConnectRemark)
//                .setTargetSignItem(targetSignItem)
//                .setTargetSignItemRemark(targetSignItemRemark)
//                .build();
//
//        return ResponseDTO.builder()
//                .setData(data)
//                .setResponse(responseMessage)
//                .build();
//    }
//
//
//    /**
//     * /inquery/QueryRewardCountById
//     */
//    private JsonNode inqueryQueryRewardCountByIdAPI(RequestDTO request) throws Exception {
//        String uri = "/inquery/QueryRewardCountById";
//
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.set("Authorization", "Basic d21mcHM6OTQ3YmY3MTE3YjkwNjc0YTc1Y2U4NTJiNTdhNjcyNTQ=");
//        requestHeaders.set("x-client-system", "EBMW");
//        requestHeaders.set("x-api-txno", "custProfileMemberBenefitsGet");
//        requestHeaders.set("datetime", "application/json");
//
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("custId", request.getCustId());
//
//        return commonJsonNodeTools(uri, requestBody, requestHeaders);
//    }
//
//
//    /**
//     * /fp-health/acct-record/action/read
//     */
//    private JsonNode fpHealthAcctRecordActionReadAPI(RequestDTO request) throws Exception {
//        String uri = "/fp-health/acct-record/action/read";
//
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.set("Authorization", "Basic d21mcHM6OTQ3YmY3MTE3YjkwNjc0YTc1Y2U4NTJiNTdhNjcyNTQ=");
//        requestHeaders.set("x-client-system", "EBMW");
//        requestHeaders.set("x-api-txno", "custProfileMemberBenefitsGet");
//        requestHeaders.set("datetime", "application/json");
//
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("custId", request.getCustId());
//
//        return commonJsonNodeTools(uri, requestBody, requestHeaders);
//    }
//
//    /**
//     * /policy-health/report-log/action/read
//     */
//    private JsonNode policyHealthReportLogActionReadAPI(RequestDTO request) throws Exception {
//        String uri = "/policy-health/report-log/action/read";
//
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.set("Authorization", "Basic d21mcHM6OTQ3YmY3MTE3YjkwNjc0YTc1Y2U4NTJiNTdhNjcyNTQ=");
//        requestHeaders.set("x-client-system", "EBMW");
//        requestHeaders.set("x-api-txno", "custProfileMemberBenefitsGet");
//        requestHeaders.set("datetime", "application/json");
//
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("custId", request.getCustId());
//
//        return commonJsonNodeTools(uri, requestBody, requestHeaders);
//    }
//
//    /**
//     * 目標達成警示查詢  /fp-health/acct-target-sign/action/read
//     */
//    private JsonNode fpHealthAcctTargetSignActionReadAPI(RequestDTO request) {
//        String uri = "/fp-health/acct-target-sign/action/read";
//
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.set("Authorization", "Basic d21mcHM6OTQ3YmY3MTE3YjkwNjc0YTc1Y2U4NTJiNTdhNjcyNTQ=");
//        requestHeaders.set("x-client-system", "EBMW");
//        requestHeaders.set("x-api-txno", "custProfileMemberBenefitsGet");
//
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("custId", request.getCustId());
//
//
//        try {
//            JsonNode jsonNode = commonJsonNodeTools(uri, requestBody, requestHeaders);
//            logger.info("目標達成警示查詢\t uri {}\n{}", uri, jsonNode);
//            return jsonNode;
//        } catch (Exception e) {
//            logger.error("目標達成警示查詢 Exception", e);
//        }
//        return null;
//    }
//
//    /**
//     * 尚未連結行內資產之健診項目查詢
//     *
//     * @param request
//     * @return
//     */
//    private JsonNode fpHealthNotAssetConnectActionReadAPI(RequestDTO request) {
//        String uri = "/fp-health/not-asset-connect/action/read";
//
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.set("Authorization", "Basic d21mcHM6OTQ3YmY3MTE3YjkwNjc0YTc1Y2U4NTJiNTdhNjcyNTQ=");
//        requestHeaders.set("x-client-system", "EBMW");
//        requestHeaders.set("x-api-txno", "custProfileMemberBenefitsGet");
//        requestHeaders.set("datetime", "application/json");
//
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("custId", request.getCustId());
//
//        try {
//            JsonNode jsonNode = commonJsonNodeTools(uri, requestBody, requestHeaders);
//            logger.info("尚未連結行內資產之健診項目查詢\t uri {}\n{}", uri, jsonNode);
//            return jsonNode;
//        } catch (Exception e) {
//            logger.error("尚未連結行內資產之健診項目查詢 Exception", e);
//        }
//
//        return null;
//    }
//
//
//    /**
//     * /caip3/wm/rewardQry
//     */
//
//    private JsonNode caip3WmRewardQryAPI(RequestDTO request) throws Exception {
//        String uriRewardQry = "/caip3/wm/rewardQry";
//        Map<String, Object> requestBodyRewardQry = new HashMap<>();
//        Map<String, Object> reqhdr = new HashMap<>();
//        reqhdr.put("ApiReqUniqueId", "{{transactionId}}");
//        reqhdr.put("ApiUserId", "CTBC_CSSI");
//        Map<String, Object> reqbdy = new HashMap<>();
//        reqbdy.put("Cid", "V225776971");
//        reqbdy.put("Birthday", "19910730");
//        reqbdy.put("ProductNo", "00000");
//        reqbdy.put("HandlerId", "Z00012345");
//        requestBodyRewardQry.put("Reqhdr", reqhdr);
//        requestBodyRewardQry.put("Reqbdy", reqbdy);
//
//        HttpHeaders requestHeadersRewardQry = new HttpHeaders();
//        requestHeadersRewardQry.set("Authorization", "Bearer 68bf91a4");
//        requestHeadersRewardQry.set("api-txno", "custProfileMemberBenefitsGet");
//        requestHeadersRewardQry.set("ctxLogId", "ctxLogIdctxLogIdctxLogId");
//        return commonJsonNodeTools(uriRewardQry, requestBodyRewardQry, requestHeadersRewardQry);
//    }
//
//    //客戶風險訊息與理財目的資訊查詢服務
//    private JsonNode daasV1FcastRiskAPI(RequestDTO request) throws Exception {
//        String uri = "/daas/v1/fcast/risk";
//
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.set("Authorization", "Basic d21mcHM6OTQ3YmY3MTE3YjkwNjc0YTc1Y2U4NTJiNTdhNjcyNTQ=");
//        requestHeaders.set("x-client-system", "EBMW");
//        requestHeaders.set("x-api-txno", "custProfileMemberBenefitsGet");
//        requestHeaders.set("datetime", "application/json");
//
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("custId", request.getCustId());
//
//        return commonJsonNodeTools(uri, requestBody, requestHeaders);
//    }
//
//    private JsonNode commonJsonNodeTools(String uri, Map<String, Object> requestBody, HttpHeaders requestHeaders) throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//        RestTemplate restTemplate = new RestTemplate();
//        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, requestHeaders);
//        ResponseEntity<String> response = restTemplate.postForEntity(url + uri, entity, String.class);
//
//        return objectMapper.readTree(response.getBody());
//
//    }
//
//    private ResponseMessage getResponseMessage(RequestDTO request, String... statusCode) throws JsonProcessingException {
//        boolean isNull = false;
////        if (request == null) isNull = true;
//        if (request.getCustId() == null || request.getCustId().isEmpty()) isNull = true;
//        if (request.getFaId() == null || request.getFaId().isEmpty()) isNull = true;
//
//        if (isNull) {
//            return ResponseMessage.INSUFFICIENT_QUERY_CONDITIONS;
//        }
//        ResponseMessage responseMessage = ResponseMessage.SUCCESS;
//        if (statusCode.length != 0) {
//            Set<String> statusCodeSet = new HashSet<>(Arrays.asList(statusCode));
//            logger.info("statusCodeSet {}", statusCodeSet);
//
//            if (statusCodeSet.contains("0000")) {
//                responseMessage = statusCodeSet.size() == 1 ? ResponseMessage.SUCCESS : ResponseMessage.DATA_SOURCE_ERROR;
//            } else if (statusCodeSet.size() == 1) {
//                responseMessage = switch (statusCodeSet.iterator().next()) {
//                    case "0000" -> ResponseMessage.SUCCESS;
//                    case "0001" -> ResponseMessage.NO_MATCHING_DATA;
//                    case "1000" -> ResponseMessage.INSUFFICIENT_QUERY_CONDITIONS;
//                    case "1001" -> ResponseMessage.QUERY_CONDITION_FORMAT_ERROR;
//                    case "2000" -> ResponseMessage.NOT_AUTHORIZED_QUERY;
//                    case "2001" -> ResponseMessage.DATA_SOURCE_ERROR;
//                    case "2002" -> ResponseMessage.DATA_SOURCE_TIMEOUT;
//                    case "2003" -> ResponseMessage.USER_NOT_FOUND;
//                    case "2004" -> ResponseMessage.ERROR_PROCESSING_KNOWLEDGE_FILE;
//                    default -> ResponseMessage.SYSTEM_ERROR;
//                };
//            } else {
//                responseMessage = ResponseMessage.SYSTEM_ERROR;
//            }
//        }
//        return responseMessage;
//    }
//}
