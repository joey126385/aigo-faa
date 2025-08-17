package com.ctbc.aigo.controller;

import com.ctbc.aigo.bean.dto.RequestDTO;
import com.ctbc.aigo.bean.dto.ResponseDTO;
import com.ctbc.aigo.service.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/k-gpt")
@Validated
public class KgptController {
    private static Logger logger = LoggerFactory.getLogger(KgptController.class);
    final AdvisoryService advisoryService;
    final BenefitNotificationsService benefitNotificationsService;
    final FinancialGoalsService kGptService;
    final MemberBenefitsService memberBenefitsService;
    final RiskDisclosuresService riskDisclosuresService;

    public KgptController(AdvisoryService advisoryService, BenefitNotificationsService benefitNotificationsService, FinancialGoalsService kGptService, MemberBenefitsService memberBenefitsService, RiskDisclosuresService riskDisclosuresService) {
        this.advisoryService = advisoryService;
        this.benefitNotificationsService = benefitNotificationsService;
        this.kGptService = kGptService;
        this.memberBenefitsService = memberBenefitsService;
        this.riskDisclosuresService = riskDisclosuresService;
    }

    /**
     * 1、個人化API-權益優惠
     * /api/v1/k-gpt/cust-profile/member-benefits/get
     */
    @PostMapping("/cust-profile/member-benefits/get")
    public ResponseDTO custProfileMemberBenefitsGet(@Valid @RequestBody RequestDTO request) throws Exception {
        logger.info("Received request: {}", request);
        return memberBenefitsService.custProfileMemberBenefitsGetService(request);
    }

    /**
     * 2、個人化API-風險訊息API
     * /api/v1/k-gpt/cust-profile/risk-disclosures/get
     */
    @PostMapping("/cust-profile/risk-disclosures/get")
    public ResponseDTO get(@Valid @RequestBody RequestDTO request) throws Exception {
        logger.info("Received request: {}", request);
        return riskDisclosuresService.get(request);
    }

    /**
     * 3、個人化API-理財目的API
     * /api/v1/k-gpt/asset-plan/financial-goals/get
     */
    @PostMapping("/asset-plan/financial-goals/get")
    public ResponseDTO assetPlanFinancialGoalsGet(@Valid @RequestBody RequestDTO request, @Valid @RequestHeader Map<String, String> headers) throws Exception {
        logger.info("Received request: {} \n{}", request, headers);
        return kGptService.get(request);
    }

    /**
     * 4、個人化API-權益通知API
     * /api/v1/k-gpt/best-offer/benefit-notifications/get
     */
    @PostMapping("/best-offer/benefit-notifications/get")
    public ResponseDTO bestOfferBenefitNotificationsGet(@Valid @RequestBody RequestDTO request) throws Exception {
        logger.info("Received request: {}", request);
        return benefitNotificationsService.get(request);
    }

    /**
     * 5、個人化API-Advisory API
     * /api/v1/k-gpt/best-offer/advisory/get
     */
    @PostMapping("/best-offer/advisory/get")
    public ResponseDTO bestOfferAdvisoryGet(@Valid @RequestBody RequestDTO request) throws Exception {
        logger.info("Received request: {}", request);
        return advisoryService.get(request);
    }


}
