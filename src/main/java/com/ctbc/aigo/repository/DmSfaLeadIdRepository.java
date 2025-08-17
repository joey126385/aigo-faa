package com.ctbc.aigo.repository;

import com.ctbc.aigo.entity.DmSfaLeadIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DmSfaLeadIdRepository  extends JpaRepository<DmSfaLeadIdEntity, String>{
    List<DmSfaLeadIdEntity> findByCustId(String custId);
    List<DmSfaLeadIdEntity> findByEmpIdOrderByEndDateDesc(String empId);
    List<DmSfaLeadIdEntity> findByEmpIdAndCustId(String empId, String custId);

//    @Query(value = "SELECT cust_id, MAX(end_date) AS latest_end_date,sfa_lead_id,ao_code , " +
//            " ao_code ,EMP_ID , createtime " +
//            "FROM dm_sfa_lead_id " +
//            "WHERE emp_id = :empId " +
//            "GROUP BY cust_id, sfa_lead_id , ao_code ,EMP_ID , createtime " +
//            "ORDER BY cust_id, latest_end_date DESC",
//            nativeQuery = true)
//    List<DmSfaLeadIdEntity> findGroupedLeadsByEmpId(@Param("empId") String empId);
}
