package com.retailer.rewards.rewardcalculatorservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sequence")
    @SequenceGenerator(name="sequence", sequenceName="myClass_pk_seq", allocationSize=1)
    @Column(name = "CUSTOMER_ID")
    private Long customerId;
    @Column(name = "CUSTOMER_NAME")
    private String customerName;
}
