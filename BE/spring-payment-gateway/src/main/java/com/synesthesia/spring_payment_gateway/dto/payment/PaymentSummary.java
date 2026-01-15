package com.synesthesia.spring_payment_gateway.dto.payment;

import com.synesthesia.spring_payment_gateway.config.PaymentStatus;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class PaymentSummary {

    private String donationId;

    private String donatorName;

    private Long amount;

    private String paymentMethod;

    private PaymentStatus paymentStatus;

    private OffsetDateTime timestamp;

}
