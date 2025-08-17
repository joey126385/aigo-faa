package com.ctbc.aigo.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Table(name = "DM_PCO_BO_RIGHTS")
@Entity
@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
public class DmPcoBoRightsEntity {

    @Id
    @Column(name = "CUSTOMER_ID",length = 32)
    private String customerId;
    @Column(name = "MEMBER_EXPIRED",length = 12)
    private String memberExpired;
    @Column(name = "MEMBER_EXPIRED_REMARK",length = 4000)
    private String memberExpiredRemark;
    @Column(name = "QUALIFICATION_EXPIRED",length = 12)
    private String qualificationExpired;
    @Column(name = "QUALIFICATION_EXPIRED_REMARK",length = 4000)
    private String qualificationExpiredRemark;
    @Column(name = "DATA_DATE")
    private LocalDate dataDate;
    @Column(name = "PROCESSING_DATE")
    private LocalDate processingDate;


}
