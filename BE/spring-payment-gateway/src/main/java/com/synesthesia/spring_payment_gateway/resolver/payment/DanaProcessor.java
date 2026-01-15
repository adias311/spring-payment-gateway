package com.synesthesia.spring_payment_gateway.resolver.payment;

import com.synesthesia.spring_payment_gateway.dto.payment.PaymentResponse;
import com.synesthesia.spring_payment_gateway.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class DanaProcessor implements PaymentProcessor {

    @Override
    public String getMethod() {
        return "DANA";
    }

    @Override
    public PaymentResponse process(Payment payment) {
        return new PaymentResponse(null);
    }
}
