package com.synesthesia.spring_payment_gateway.model;

import com.synesthesia.spring_payment_gateway.config.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class Payment {

    private String donationId;

    private String donatorName;

    private Long amount;

    private String paymentMethod;

    private PaymentStatus paymentStatus;

    private OffsetDateTime timestamp;
}
