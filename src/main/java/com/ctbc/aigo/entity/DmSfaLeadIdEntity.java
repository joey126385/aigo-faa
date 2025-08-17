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
import java.time.LocalDateTime;

@Data
@Table(name = "DM_SFA_LEAD_ID")
@Entity
@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
public class DmSfaLeadIdEntity {
    @Id
    @Column(name = "SFA_LEAD_ID",length = 43)
    private String sfaLeadId;
    @Column(name = "LEAD_NAME",length = 300)
    private String leadName;
    @Column(name = "CUST_ID",length = 16)
    private String custId;
    @Column(name = "CUST_CHN_NAME",length = 160)
    private String custChnName;
    @Column(name = "LEAD_STATUS",length = 4)
    private String leadStatus;
    @Column(name = "LEAD_STATUS_NAME",length = 6)
    private String leadStatusName;
    @Column(name = "LEAD_SOURCE_ID",length = 2)
    private String leadSourceId;
    @Column(name = "SALES_PRIORITY",length = 2)
    private String salesPriority;
    @Column(name = "SALES_PRIORITY_NAME",length = 4)
    private String salesPriorityName;
    @Column(name = "START_DATE")
    private LocalDate startDate;
    @Column(name = "END_DATE")
    private LocalDate endDate;
    @Column(name = "AO_CODE",length = 10)
    private String aoCode;
    @Column(name = "EMP_ID",length = 9)
    private String empId;
    @Column(name = "CREATOR",length = 20)
    private String creator;
    @Column(name = "CREATETIME")
    private LocalDateTime createTime;
    @Column(name = "MODIFIER",length = 20)
    private String modifier;
    @Column(name = "LASTUPDATE")
    private LocalDateTime lastUpdate;
    @Column(name = "PROCESSING_DATE")
    private LocalDate processingDate;
    @Column(name = "DM_DATE")
    private LocalDate dmDate;
}
