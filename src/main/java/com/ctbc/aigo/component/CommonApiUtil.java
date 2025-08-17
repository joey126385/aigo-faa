package com.ctbc.aigo.component;

import com.ctbc.aigo.bean.dto.RequestDTO;
import com.ctbc.aigo.bean.ResponseMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class CommonApiUtil {

    private static Logger logger = LoggerFactory.getLogger(CommonApiUtil.class);


    //daas
    @Value("${daas.api.base-url}")
    private String daasApiBaseUrl;
    @Value("${daas.api.auth.x-client-system}")
    private String daasApiAuthXClientSystem;
    @Value("${daas.api.auth.username}")
    private String daasApiAuthUsername;
    @Value("${daas.api.auth.password}")
    private String daasApiAuthPassword;

    @Value("${wmfps.api.base-url}")
    private String wmfpsApiBaseUrl;


    //CAIP API
    @Value("${caip.api.base-url}")
    private String caipApiBaseUrl;
    @Value("${caip.api.auth.username}")
    private String caipApiAuthUsername;
    @Value("${caip.api.auth.password}")
    private String caipApiAuthPassword;
    @Value("${caip.api.auth.x-client-system}")
    private String caipApiAuthXClientSystem;

    //CAIP3 API
    @Value("${caip3.api.base-url}")
    private String caip3ApiBaseUrl;
    @Value("${caip3.api.auth.username}")
    private String caip3ApiAuthUsername;
    @Value("${caip3.api.auth.password}")
    private String caip3ApiAuthPassword;

    @Value("${ref.api.base-url}")
    private String refApiBaseUrl;

    public ResponseMessage getResponseMessage(RequestDTO request, String... statusCode) {
        // check the conditions
        boolean isNull = false;

        if (request.getCustId() == null || request.getCustId().isEmpty()) isNull = true;
        if (request.getFaId() == null || request.getFaId().isEmpty()) isNull = true;

        if (isNull) {
            return ResponseMessage.INSUFFICIENT_QUERY_CONDITIONS;
        }

        ResponseMessage responseMessage = ResponseMessage.SUCCESS;
        if (statusCode.length != 0) {
            Set<String> statusCodeSet = new HashSet<>(Arrays.asList(statusCode));
            logger.info("statusCodeSet {}", statusCodeSet);

            if (statusCodeSet.contains("0000")) {
                responseMessage = statusCodeSet.size() == 1 ? ResponseMessage.SUCCESS : ResponseMessage.DATA_SOURCE_ERROR;
            } else if (statusCodeSet.size() == 1) {
                responseMessage = switch (statusCodeSet.iterator().next()) {
                    case "0000" -> ResponseMessage.SUCCESS;
                    case "0001" -> ResponseMessage.NO_MATCHING_DATA;
                    case "1000" -> ResponseMessage.INSUFFICIENT_QUERY_CONDITIONS;
                    case "1001" -> ResponseMessage.QUERY_CONDITION_FORMAT_ERROR;
                    case "2000" -> ResponseMessage.NOT_AUTHORIZED_QUERY;
                    case "2001" -> ResponseMessage.DATA_SOURCE_ERROR;
                    case "2002" -> ResponseMessage.DATA_SOURCE_TIMEOUT;
                    case "2003" -> ResponseMessage.USER_NOT_FOUND;
                    case "2004" -> ResponseMessage.ERROR_PROCESSING_KNOWLEDGE_FILE;
                    default -> ResponseMessage.SYSTEM_ERROR;
                };
            } else {
                responseMessage = ResponseMessage.SYSTEM_ERROR;
            }
        }
        return responseMessage;
    }

    private JsonNode commonJsonNodeTools(String url, String uri, Map<String, Object> requestBody, HttpHeaders requestHeaders) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        RestTemplate restTemplate = new RestTemplate();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, requestHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(url + uri, entity, String.class);

        return objectMapper.readTree(response.getBody());

    }

    private JsonNode commonJsonNodeTools(String url, Map<String, Object> requestBody, HttpHeaders requestHeaders) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        RestTemplate restTemplate = new RestTemplate();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, requestHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        return objectMapper.readTree(response.getBody());

    }

    /**
     * Source: ref
     * refRefrInfoActionReadAPI
     *
     * @param request
     * @return
     * @throws Exception
     */
    public JsonNode refRefrInfoActionReadAPI(RequestDTO request) throws Exception {
        String uri = "/ref/refr-info/action/read";

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Authorization", "Basic d21mcHM6OTQ3YmY3MTE3YjkwNjc0YTc1Y2U4NTJiNTdhNjcyNTQ=");
        requestHeaders.set("x-client-system", "EBMW");
        requestHeaders.set("x-api-txno", "EBMW20240119000000000");
        requestHeaders.set("datetime", ZonedDateTime.now().toString());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("custId", request.getCustId());

        return commonJsonNodeTools(refApiBaseUrl, uri, requestBody, requestHeaders);
    }


    /**
     * Source: CAIP
     * RW100a 歸戶查詢信用卡權益次數  QueryRewardById
     * /inquery/QueryRewardCountById
     *
     * @param request
     */
    public JsonNode inqueryQueryRewardCountByIdAPI(RequestDTO request) throws Exception {
        String uri = "/inquery/QueryRewardCountById";

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBasicAuth(caipApiAuthUsername, caipApiAuthPassword);
        requestHeaders.set("Content-Type", "application/json");
        requestHeaders.set("x-client-system", caipApiAuthXClientSystem);
        requestHeaders.set("x-api-txno", getXApiTxno("CAIP"));
        requestHeaders.set("datetime", ZonedDateTime.now().toString());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("custId", request.getCustId());

        return commonJsonNodeTools(caipApiBaseUrl, requestBody, requestHeaders);
    }

    /**
     * /fp-health/acct-record/action/read
     */
    public JsonNode fpHealthAcctRecordActionReadAPI(RequestDTO request) throws Exception {
        String uri = "/fp-health/acct-record/action/read";

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Authorization", "Basic d21mcHM6OTQ3YmY3MTE3YjkwNjc0YTc1Y2U4NTJiNTdhNjcyNTQ=");
        requestHeaders.set("x-client-system", "EBMW");
        requestHeaders.set("x-api-txno", "custProfileMemberBenefitsGet");
        requestHeaders.set("datetime", ZonedDateTime.now().toString());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("custId", request.getCustId());

        return commonJsonNodeTools(wmfpsApiBaseUrl, uri, requestBody, requestHeaders);
    }

    /**
     * /policy-health/report-log/action/read
     */
    public JsonNode policyHealthReportLogActionReadAPI(RequestDTO request) throws Exception {
        String uri = "/policy-health/report-log/action/read";

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Authorization", "Basic d21mcHM6OTQ3YmY3MTE3YjkwNjc0YTc1Y2U4NTJiNTdhNjcyNTQ=");
        requestHeaders.set("x-client-system", "EBMW");
        requestHeaders.set("x-api-txno", "custProfileMemberBenefitsGet");
        requestHeaders.set("datetime", ZonedDateTime.now().toString());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("custId", request.getCustId());

        return commonJsonNodeTools(wmfpsApiBaseUrl, uri, requestBody, requestHeaders);
    }

    /**
     * 目標達成警示查詢  /fp-health/acct-target-sign/action/read
     */
    public JsonNode fpHealthAcctTargetSignActionReadAPI(RequestDTO request) {
        String uri = "/fp-health/acct-target-sign/action/read";

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Authorization", "Basic d21mcHM6OTQ3YmY3MTE3YjkwNjc0YTc1Y2U4NTJiNTdhNjcyNTQ=");
        requestHeaders.set("x-client-system", "EBMW");
        requestHeaders.set("x-api-txno", "custProfileMemberBenefitsGet");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("custId", request.getCustId());


        try {
            JsonNode jsonNode = commonJsonNodeTools(wmfpsApiBaseUrl, uri, requestBody, requestHeaders);
            logger.info("目標達成警示查詢\t uri {}\n{}", uri, jsonNode);
            return jsonNode;
        } catch (Exception e) {
            logger.error("目標達成警示查詢 Exception", e);
        }
        return null;
    }

    /**
     * 尚未連結行內資產之健診項目查詢
     *
     * @param request
     * @return
     */
    public JsonNode fpHealthNotAssetConnectActionReadAPI(RequestDTO request) {
        String uri = "/fp-health/not-asset-connect/action/read";

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Authorization", "Basic d21mcHM6OTQ3YmY3MTE3YjkwNjc0YTc1Y2U4NTJiNTdhNjcyNTQ=");
        requestHeaders.set("x-client-system", "EBMW");
        requestHeaders.set("x-api-txno", "custProfileMemberBenefitsGet");
        requestHeaders.set("datetime", ZonedDateTime.now().toString());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("custId", request.getCustId());

        try {
            JsonNode jsonNode = commonJsonNodeTools(wmfpsApiBaseUrl, uri, requestBody, requestHeaders);
            logger.info("尚未連結行內資產之健診項目查詢\t uri {}\n{}", uri, jsonNode);
            return jsonNode;
        } catch (Exception e) {
            logger.error("尚未連結行內資產之健診項目查詢 Exception", e);
        }

        return null;
    }


    /**
     * WM001  財管權益次數查詢
     * @param request
     * @return
     * @throws Exception
     */
    public JsonNode caip3WmRewardQryAPI(RequestDTO request) throws Exception {
        String uriRewardQry = "/caip3/wm/rewardQry";
        Map<String, Object> requestBodyRewardQry = new HashMap<>();
        Map<String, Object> reqhdr = new HashMap<>();
        reqhdr.put("ApiReqUniqueId", "{{transactionId}}");
        reqhdr.put("ApiUserId", "CTBC_CSSI");
        Map<String, Object> reqbdy = new HashMap<>();
        reqbdy.put("Cid", "V225776971");
        reqbdy.put("Birthday", "19910730");
        reqbdy.put("ProductNo", "00000");
        reqbdy.put("HandlerId", "Z00012345");
        requestBodyRewardQry.put("Reqhdr", reqhdr);
        requestBodyRewardQry.put("Reqbdy", reqbdy);

        HttpHeaders requestHeadersRewardQry = new HttpHeaders();
        requestHeadersRewardQry.setBasicAuth(caip3ApiAuthUsername, caip3ApiAuthPassword);
        requestHeadersRewardQry.setContentType(MediaType.APPLICATION_JSON);

        requestHeadersRewardQry.set("api-txno",  getXApiTxno("CAIP3"));
        requestHeadersRewardQry.set("ctxLogId", "ctxLogIdctxLogIdctxLogId");

        return commonJsonNodeTools(caip3ApiBaseUrl, requestBodyRewardQry, requestHeadersRewardQry);
    }

    //客戶風險訊息與理財目的資訊查詢服務
    public JsonNode daasV1FcastRiskAPI(RequestDTO request) throws Exception {
        String uri = "/daas/v1/fcast/risk";

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Authorization", "Basic d21mcHM6OTQ3YmY3MTE3YjkwNjc0YTc1Y2U4NTJiNTdhNjcyNTQ=");
        requestHeaders.set("x-client-system", "EBMW");
        requestHeaders.set("x-api-txno", "custProfileMemberBenefitsGet");
        requestHeaders.set("datetime", ZonedDateTime.now().toString());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("custId", request.getCustId());

        return commonJsonNodeTools(daasApiBaseUrl, uri, requestBody, requestHeaders);
    }

    /**
     * 客戶權益優惠的資訊查詢服務
     *
     * @param request
     * @return
     * @throws Exception
     */
    public JsonNode daasV1FcastUserProfileAPI(RequestDTO request) throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setBasicAuth(daasApiAuthUsername, daasApiAuthPassword);
        requestHeaders.set("x-client-system", daasApiAuthXClientSystem);
        requestHeaders.set("x-api-txno", getXApiTxno("DAAS"));
        requestHeaders.set("datetime", ZonedDateTime.now().toString());
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("custId", request.getCustId());
        return commonJsonNodeTools(daasApiBaseUrl, requestBody, requestHeaders);
    }

    /**
     * 共25碼 :
     * client應用系統代號(至多6碼)+
     * YYYY(4碼) + MM(2碼) +DD(2碼) + HH(24小時制2碼) + mm(2碼) + ss(2碼) + ms(豪秒3碼) +
     * <p>
     * 流水號(至多5碼)。
     * <p>
     * 應用系統代號+流水號不可超過8碼，流水號長度取[8 - 應用系統代號長度]。
     * 範例: client應用系統代號+YYYYMMDDHHmmssfff (maxLength = 25)
     *
     * @return
     */
    public String getXApiTxno(String clientCode) {

        int sequenceLength = 8 - clientCode.length();
        int maxSequenceValue = (int) Math.pow(10, sequenceLength) - 1;
        int currentSequence = 1;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");

        // 組成各部分
        String datePart = now.format(dateFormatter);           // YYYYMMDD (8碼)
        String timePart = now.format(timeFormatter);           // HHmmss (6碼)
        String millisPart = String.format("%03d",
                now.getNano() / 1_000_000);                        // 毫秒 (3碼)
        String sequencePart = String.format("%0" + sequenceLength + "d",
                currentSequence);                                  // 流水號

        // 組合完整ID
        String fullId = clientCode + datePart + timePart + millisPart + sequencePart;

        // 更新流水號
        if (currentSequence >= maxSequenceValue) {
            currentSequence = 1; // 重置流水號
        } else {
            currentSequence++;
        }

        return fullId;
    }
}
