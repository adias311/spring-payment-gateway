package com.synesthesia.spring_payment_gateway.resolver.payment;


import com.synesthesia.spring_payment_gateway.dto.payment.PaymentResponse;
import com.synesthesia.spring_payment_gateway.dto.xendit.XenditPaymentRequest;
import com.synesthesia.spring_payment_gateway.dto.xendit.XenditPaymentResponse;
import com.synesthesia.spring_payment_gateway.dto.xendit.channel.ShopepayChannelProperties;
import com.synesthesia.spring_payment_gateway.helper.payment.PaymentHelper;
import com.synesthesia.spring_payment_gateway.model.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShopeePayProcessor implements PaymentProcessor {

    private final PaymentHelper paymentHelper;
    private final RestClient xenditClient;

    @Value("${xendit.success.url}")
    private String successUrl;

    @Value("${xendit.failure.url}")
    private String failureUrl;

    @Override
    public String getMethod() {
        return "SHOPEEPAY";
    }

    @Override
    public PaymentResponse process(Payment payment) {

            XenditPaymentRequest request = new XenditPaymentRequest();
            request.setReferenceId(payment.getDonationId());
            request.setType("PAY");
            request.setCountry("ID");
            request.setCurrency("IDR");
            request.setRequestAmount(payment.getAmount());
            request.setCaptureMethod("AUTOMATIC");
            request.setChannelCode(payment.getPaymentMethod());
            request.setDescription("Donation");

            var channelProperties = new ShopepayChannelProperties();
            channelProperties.setSuccessReturnUrl(paymentHelper.buildSuccessUrl(
                    successUrl,
                    payment.getDonationId()
            ));
            channelProperties.setFailureReturnUrl(failureUrl);
            request.setChannelProperties(channelProperties);

        var xenditPaymentResponse = xenditClient.post()
                .uri("/v3/payment_requests")
                .body(request)
                .retrieve()
                .body(XenditPaymentResponse.class);

        paymentHelper.deserializeChannelProperties(xenditPaymentResponse);
        return new PaymentResponse(xenditPaymentResponse);
    }

}
