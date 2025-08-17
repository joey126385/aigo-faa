package com.ctbc.aigo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Table(name = "DM_FA_CUST")
@Entity
@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
public class DmFaCustEntity {

    @Id
    @Column(name = "AO_NO")
    private String aoNo;
    @Column(name = "AO_EMP_NO")
    private String aoEmpNo;
    @Column(name = "AO_NAME")
    private String aoName;
    @Column(name = "CUSTOMER_ID")
    private String customerId;
    @Column(name = "CUST_CHN_NAME")
    private String custChnName;
    @Column(name = "CUST_ENG_NAME")
    private String custEngName;
}
