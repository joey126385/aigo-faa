package com.ctbc.aigo.service;


import com.ctbc.aigo.bean.dto.RequestDTO;
import com.ctbc.aigo.bean.dto.ResponseDTO;

public interface AdoptionService {
    ResponseDTO adoptionList(RequestDTO request);
}
