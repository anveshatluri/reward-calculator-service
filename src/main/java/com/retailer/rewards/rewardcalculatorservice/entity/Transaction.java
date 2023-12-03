package com.retailer.rewards.rewardcalculatorservice.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Entity
@Table(name = "TRANSACTION")
public class Transaction {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID")
    private Long transactionId;

    @Column(name="CUSTOMER_ID")
    private Long customerId;

    @Column(name = "TRANSACTION_DATE")
    private Timestamp transactionDate;

    @Column(name = "AMOUNT")
    private double transactionAmount;


    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}
