package com.ctbc.aigo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table(name = "DM_CUST360")
@Entity
@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
public class DmCust360Entity {
    @Id
    @Column(name = "CUSTOMER_ID",length = 32)
    private String customerId;
    @Column(name = "FUNDING_NEEDS",length = 8)
    private String fundingNeeds;
    @Column(name = "FUNDING_NEEDS_REMARK",length = 4000)
    private String fundingNeedsRemark;
    @Column(name = "FORIEGN_CURRENCY_NEEDS",length = 12)
    private String foriegnCurrencyNeeds;
    @Column(name = "FORIEGN_CURRENCY_NEEDS_REMARK",length = 4000)
    private String foriegnCurrencyNeedsRemark;
    @Column(name = "INSURANCE_NEEDS",length = 8)
    private String insuranceNeeds;
    @Column(name = "INSURANCE_NEEDS_REMARK",length = 4000)
    private String insuranceNeedsRemark;
    @Column(name = "ETF_NEEDS",length = 7)
    private String etfNeeds;
    @Column(name = "ETF_NEEDS_REMARK",length = 4000)
    private String etfNeedsRemark;
    @Column(name = "HOUSE_FINANCING_NEEDS",length = 8)
    private String houseFinancingNeeds;
    @Column(name = "HOUSE_FINANCING_NEEDS_REMARK",length = 4000)
    private String houseFinancingNeedsRemark;
    @Column(name = "HEALTH_CHECK_NEEDS",length = 12)
    private String healthCheckNeeds;
    @Column(name = "HEALTH_CHECK_NEEDS_REMARK",length = 4000)
    private String healthCheckNeedsRemark;
    @Column(name = "LIFE_STAGE_CHANGES",length = 16)
    private String lifeStageChanges;
    @Column(name = "LIFE_STAGE_CHANGES_REMARK",length = 4000)
    private String lifeStageChangesRemark;
    @Column(name = "DATA_DATE")
    private LocalDate dataDate;
    @Column(name = "PROCESSING_DATE")
    private LocalDate processingDate;
    @Column(name = "DM_DATE")
    private LocalDateTime dmDate;




}
