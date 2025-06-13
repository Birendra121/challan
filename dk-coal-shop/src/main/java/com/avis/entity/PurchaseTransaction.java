package com.avis.entity;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "purchase_transaction")
public class PurchaseTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sellerName;
    private String invoiceNo;
    private String deliveryNoteNo;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String material;
    private BigDecimal weight;
    private BigDecimal rate;
    private BigDecimal gstPercent;
    private BigDecimal amount;
    private BigDecimal gstAmount;
    private BigDecimal totalAmount;

    private String totalAmountInWords;
}
