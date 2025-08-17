package com.ctbc.aigo.service.impl;

import com.ctbc.aigo.bean.ResponseMessage;
import com.ctbc.aigo.bean.dto.CustProfileMemberBenefitsGetResponseDataDTO;
import com.ctbc.aigo.bean.dto.RequestDTO;
import com.ctbc.aigo.bean.dto.ResponseDTO;
import com.ctbc.aigo.component.CommonApiUtil;
import com.ctbc.aigo.service.AdoptionService;
import com.ctbc.aigo.service.MemberBenefitsService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import static com.ctbc.aigo.module.aesencryptor.AesEncryptor.getSsoUrl;

@Service
public class MemberBenefitsServiceImpl implements MemberBenefitsService {

    final AdoptionService adoptionService;

    final CommonApiUtil commonApiUtil;

    public MemberBenefitsServiceImpl(AdoptionService adoptionService, CommonApiUtil commonApiUtil) {
        this.adoptionService = adoptionService;
        this.commonApiUtil = commonApiUtil;
    }

    /**
     * 權益優惠API
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ResponseDTO custProfileMemberBenefitsGetService(RequestDTO request) throws Exception {

        ResponseDTO adoptionListResponse = adoptionService.adoptionList(request);


        //
        if (!adoptionListResponse.getStatusCode().equals("0000")) {
            return adoptionListResponse;
        }

        //客戶權益優惠的資訊查詢服務
        JsonNode jsonNodeUserProfile = commonApiUtil.daasV1FcastUserProfileAPI(request);

        //RW100a 歸戶查詢信用卡權益次數 QueryRewardById
        JsonNode jsonQueryRewardCountById = commonApiUtil.inqueryQueryRewardCountByIdAPI(request);

        //WM001  財管權益次數查詢
        JsonNode jsonQueryRewardQry = commonApiUtil.caip3WmRewardQryAPI(request);



        //信用卡年累積消費
        int last12MonthProcessTotal = 0;
        String[] offerInfos = {"airportPark", "vipRoom", "vipCal", "airportPark", "parking", "roadSideRescue"};
        for (String offerInfo : offerInfos) {
            last12MonthProcessTotal += jsonQueryRewardCountById.get("offerInfo").get(offerInfo).get("leftTimes").asInt();
        }

        ResponseMessage responseMessage = commonApiUtil.getResponseMessage(
                request,
                jsonNodeUserProfile.get("statusCode").asText(),
                jsonQueryRewardQry.get("Reshdr").get("ResultCode").asText(),
                jsonQueryRewardCountById.get("status").asText()
        );

        if (!responseMessage.getCode().equals("0000")) {
            return ResponseDTO.builder()
                    .setResponse(responseMessage)
                    .build();
        }
        System.out.println(jsonNodeUserProfile.get("data").get("vipSegmentHist"));
        //ResponseData
        CustProfileMemberBenefitsGetResponseDataDTO data = CustProfileMemberBenefitsGetResponseDataDTO.builder()
                .setCustSsoUrl(getSsoUrl(jsonNodeUserProfile.get("data").get("custId").asText(), "CSFPG100"))
                .setVipSegmentHist(jsonNodeUserProfile.get("data").get("vipSegmentHist"))
                .setVipDegree(jsonNodeUserProfile.get("data").get("vipDegree"))
                .setFamilyVipDegree(jsonNodeUserProfile.get("data").get("familyVipDegree"))
                .setIdvTagDate(jsonNodeUserProfile.get("data").get("idvTagDate"))
                .setFamilyNextReviewDate(jsonNodeUserProfile.get("data").get("familyNextReviewDate"))
                .setAumWgItems(jsonNodeUserProfile.get("data").get("aumWgItems"))
                .setAlumItems(jsonNodeUserProfile.get("data").get("alumItems"))
                .setAumWhItems(jsonNodeUserProfile.get("data").get("aumWhItems"))
                .setPriorityLevel(jsonNodeUserProfile.get("data").get("priorityLevel"))
                .setSignedRelatives(jsonNodeUserProfile.get("data").get("signedRelatives"))
                .setWealthPointThisYear(jsonNodeUserProfile.get("data").get("wealthPointThisYear"))
                .setWealthPointNextYear(jsonNodeUserProfile.get("data").get("wealthPointNextYear"))
                .setWealthPointExpire(jsonNodeUserProfile.get("data").get("wealthPointExpire"))
                .setCreditCardCount(jsonNodeUserProfile.get("data").get("creditCardCount"))
                .setCreditCardLimitAmt(jsonNodeUserProfile.get("data").get("creditCardLimitAmt"))
                .setCreditcardReward(jsonNodeUserProfile.get("data").get("creditcardReward"))
                .setRewardResult(jsonQueryRewardQry.get("Resbdy").get("RewardResult"))
                .setLast12MonthProcess(last12MonthProcessTotal + "")
                .setAirportTrans(jsonQueryRewardCountById.get("offerInfo").get("airportPark").get("leftTimes"))
                .setVipRoom(jsonQueryRewardCountById.get("offerInfo").get("vipRoom").get("leftTimes"))
                .setVipCal(jsonQueryRewardCountById.get("offerInfo").get("vipCal").get("leftTimes"))
                .setAirportPark(jsonQueryRewardCountById.get("offerInfo").get("airportPark").get("leftTimes"))
                .setParking(jsonQueryRewardCountById.get("offerInfo").get("parking").get("leftTimes"))
                .setRoadSideRescue(jsonQueryRewardCountById.get("offerInfo").get("roadSideRescue").get("leftTimes"))
                .build();
        return ResponseDTO.builder()
                .setData(data)
                .setResponse(responseMessage)
                .build();
    }

}
