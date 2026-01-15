package com.synesthesia.spring_payment_gateway.dto.payment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentRequest {

    private String donatorName;

    private Long amount;

    private String paymentMethod;

}
