package com.synesthesia.spring_payment_gateway.dto.payment;

import com.synesthesia.spring_payment_gateway.dto.xendit.XenditPaymentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponse {

    private XenditPaymentResponse paymentResponse;

}
