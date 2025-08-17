package com.ctbc.aigo.service.impl;

import com.ctbc.aigo.bean.dto.AdoptionListResponseDataDTO;
import com.ctbc.aigo.bean.dto.RequestDTO;
import com.ctbc.aigo.bean.dto.ResponseDTO;
import com.ctbc.aigo.bean.ResponseMessage;
import com.ctbc.aigo.entity.DmFaCustEntity;
import com.ctbc.aigo.repository.DmFaCustRepository;
import com.ctbc.aigo.service.AdoptionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdoptionServiceImpl implements AdoptionService {

    final DmFaCustRepository dmFaCustRepository;


    public AdoptionServiceImpl(DmFaCustRepository dmFaCustRepository) {
        this.dmFaCustRepository = dmFaCustRepository;
    }


    @Override
    public ResponseDTO adoptionList(RequestDTO request) {
        ResponseMessage responseMessage = ResponseMessage.SUCCESS;

        if (request.getFaId() == null || request.getFaId().isEmpty()) {
            return ResponseDTO.builder()
                    .setResponse(ResponseMessage.INSUFFICIENT_QUERY_CONDITIONS)
                    .build();
        }

        /**
         * 1. 提供理專員編: 返回認養清單中所有客戶ID與姓名
         * 2. 提供理專員編+客戶姓名: 返回認養清單中的客戶ID與姓名
         * 3. 提供理專員編+客戶ID: 返回認養清單中的客戶ID與姓名
         */
        List<DmFaCustEntity> custEntities = null;
        if (request.getCustId() != null) {
            custEntities = dmFaCustRepository.findByCustomerIdAndAoEmpNo(request.getCustId(), request.getFaId());
        } else if (request.getCustName() != null) {
            custEntities = dmFaCustRepository.findByAoEmpNoAndCustChnName(request.getFaId(), request.getCustName());
        } else {
            custEntities = dmFaCustRepository.findByAoEmpNo(request.getFaId());
        }


        if (custEntities.isEmpty()){
            return ResponseDTO.builder()
                    .setResponse(ResponseMessage.USER_NOT_FOUND)
                    .build();
        }

        List<AdoptionListResponseDataDTO.adoption> adoptionList = custEntities.stream()
                .map(entity -> AdoptionListResponseDataDTO.adoption.builder()
                        .setCustId(entity.getCustomerId())
                        .setCustName(entity.getCustChnName())
                        .build())
                .collect(Collectors.toList());

        AdoptionListResponseDataDTO data = AdoptionListResponseDataDTO.builder()
                .setAdoptionList(adoptionList)
                .build();

        return ResponseDTO.builder()
                .setData(data)
                .setResponse(responseMessage)
                .build();
    }
}
