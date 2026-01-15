package com.synesthesia.spring_payment_gateway.resolver.payment;

import com.synesthesia.spring_payment_gateway.dto.payment.PaymentResponse;
import com.synesthesia.spring_payment_gateway.model.Payment;

public interface PaymentProcessor {

    String getMethod();

    PaymentResponse process(Payment payment);

}
