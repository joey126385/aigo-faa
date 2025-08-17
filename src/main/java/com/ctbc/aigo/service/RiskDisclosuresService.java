package com.ctbc.aigo.service;

import com.ctbc.aigo.bean.dto.RequestDTO;
import com.ctbc.aigo.bean.dto.ResponseDTO;
import jakarta.validation.Valid;

public interface RiskDisclosuresService {
    ResponseDTO get(@Valid RequestDTO request) throws Exception;
}
