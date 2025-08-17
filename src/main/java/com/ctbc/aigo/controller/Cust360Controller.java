package com.ctbc.aigo.controller;

import com.ctbc.aigo.bean.dto.RequestDTO;
import com.ctbc.aigo.bean.dto.ResponseDTO;
import com.ctbc.aigo.service.Cust360Service;
import com.ctbc.aigo.service.LeadsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cust-360")
public class Cust360Controller {
    private static Logger logger = LoggerFactory.getLogger(Cust360Controller.class);

    final Cust360Service cust360Service;
    final LeadsService leadsService;

    public Cust360Controller(Cust360Service cust360Service, LeadsService leadsService) {
        this.cust360Service = cust360Service;
        this.leadsService = leadsService;
    }

    /**
     * 【取得客戶資訊API】
     * /api/v1/cust-360/best-offer/cust360/get
     */
    @PostMapping("/best-offer/cust-360/get")
    public ResponseDTO get(@RequestBody RequestDTO request) throws Exception {
        logger.info("bestOfferCust360Get request: {}", request);
        return cust360Service.get(request);
    }

    /**
     * 【取得名單API】
     * /api/v1/cust-360/best-offer/leads/list
     */
    @PostMapping("/best-offer/leads/list")
    public ResponseDTO list(@RequestBody RequestDTO request) throws Exception {
        logger.info("bestOfferLeadsList request: {}", request);
        return leadsService.list(request);
    }

}
