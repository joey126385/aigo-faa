package com.ctbc.aigo.service;

import com.ctbc.aigo.bean.dto.RequestDTO;
import com.ctbc.aigo.bean.dto.ResponseDTO;


public interface KGptService {

    ResponseDTO custProfileMemberBenefitsGetService(RequestDTO request)throws Exception;

    ResponseDTO custProfileRiskDisclosuresGetService(RequestDTO request)throws Exception;

    ResponseDTO assetPlanFinancialGoalsGetService(RequestDTO request)throws Exception;

    ResponseDTO bestOfferBenefitNotificationsGetService(RequestDTO request)throws Exception;

    ResponseDTO bestOfferAdvisoryGetService(RequestDTO request)throws Exception;
}
