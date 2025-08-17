package com.ctbc.aigo.repository;

import com.ctbc.aigo.entity.DmFaCustEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DmFaCustRepository extends JpaRepository<DmFaCustEntity, String> {
    List<DmFaCustEntity> findByCustomerIdAndAoEmpNo(String customerId,String aoEmpNo);
    DmFaCustEntity findByCustomerId(String customerId);
    List<DmFaCustEntity> findByAoEmpNo(String aoEmpNo);
    List<DmFaCustEntity> findByAoEmpNoAndCustChnName(String aoEmpNo,String custChnName);
}
