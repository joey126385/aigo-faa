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
@Table(name = "DM_PCO_BO_ADVISORY")
@Entity
@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
public class DmPcoBoAdvisoryEntity {
    /**
     * CREATE TABLE [dbo].[DM_PCO_BO_ADVISORY](
     * 	[CUSTOMER_ID] [varchar](32) NOT NULL,
     * 	[HEALTH_CHECK_UPDATE_REMINDER] [varchar](16) NULL,
     * 	[HEALTH_CHECK_UPDATE_REMINDER_REMARK] [nvarchar](4000) NULL,
     * 	[FINANCIAL_HEALTH_CHECK_PENDING] [varchar](14) NULL,
     * 	[FINANCIAL_HEALTH_CHECK_PENDING_REMARK] [nvarchar](4000) NULL,
     * 	[DATA_DATE] [date] NULL,
     * 	[PROCESSING_DATE] [date] NULL
     * ) ON [PRIMARY]
     */

    @Id
    @Column(name = "CUSTOMER_ID",length = 32)
    private String customerId;
    @Column(name = "HEALTH_CHECK_UPDATE_REMINDER",length = 16)
    private String healthCheckUpdateReminder;
    @Column(name = "HEALTH_CHECK_UPDATE_REMINDER_REMARK",length = 4000)
    private String healthCheckUpdateReminderRemark;
    @Column(name = "FINANCIAL_HEALTH_CHECK_PENDING",length = 14)
    private String financialHealthCheckPending;
    @Column(name = "FINANCIAL_HEALTH_CHECK_PENDING_REMARK",length = 4000)
    private String financialHealthCheckPendingRemark;
    @Column(name = "DATA_DATE")
    private LocalDate dataDate;
    @Column(name = "PROCESSING_DATE")
    private LocalDate processingDate;

}
