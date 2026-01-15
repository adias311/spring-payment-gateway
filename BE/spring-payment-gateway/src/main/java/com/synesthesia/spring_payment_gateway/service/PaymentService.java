package com.synesthesia.spring_payment_gateway.service;

import com.synesthesia.spring_payment_gateway.config.PaymentStatus;
import com.synesthesia.spring_payment_gateway.dto.payment.PaymentRequest;
import com.synesthesia.spring_payment_gateway.dto.payment.PaymentWebhook;
import com.synesthesia.spring_payment_gateway.dto.payment.PaymentResponse;
import com.synesthesia.spring_payment_gateway.dto.payment.PaymentSummary;
import com.synesthesia.spring_payment_gateway.helper.payment.PaymentHelper;
import com.synesthesia.spring_payment_gateway.repository.PaymentRepository;
import com.synesthesia.spring_payment_gateway.resolver.payment.PaymentProcessorResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentProcessorResolver paymentResolver;
    private final PaymentHelper paymentHelper;
    private final ModelMapper modelMapper;

    public PaymentResponse createPayment(PaymentRequest request) {
       var payment = paymentHelper.mapToPayment(request);

       try {
           var processor = paymentResolver.resolve(request.getPaymentMethod());
           paymentRepository.savePayment(payment);

           return processor.process(payment);
       } catch (Exception ex) {
           try {
               paymentRepository.updatePaymentStatus(
                       payment.getDonationId(),
                       PaymentStatus.FAILED,
                       "failed create payment :" + ex.getMessage()
               );
           } catch (Exception e) {
               log.error("Failed to persist failed payment {}", e.getMessage());
           }
           throw new RuntimeException(ex);
       }

    }

    public Set<PaymentSummary> getAllPayments() {
        return paymentRepository.getAllPayment()
                .filter(Objects::nonNull)
                .map(entity -> modelMapper.map(entity, PaymentSummary.class))
                .collect(Collectors.toSet());
    }

    public void handlePaymentWebhook(PaymentWebhook webhook) {
        var event = webhook.getEvent();
        var status = webhook.getData().getStatus();
        var donationId = webhook.getData().getDonationId();
        var reason = "event " + event + " from webhook";

        switch (event) {
            case "payment.capture":
                if ("SUCCEEDED".equals(status)) {
                    paymentRepository.updatePaymentStatus(
                            donationId,
                            PaymentStatus.SUCCESS,
                            reason
                    );
                }
                break;
            case "payment.expiry":
               paymentRepository.updatePaymentStatus(
                        donationId,
                        PaymentStatus.EXPIRED,
                        reason
                );
                break;
            case "payment.failure":
                paymentRepository.updatePaymentStatus(
                        donationId,
                        PaymentStatus.FAILED,
                        reason
                );
                break;
            default:
                log.warn("Unhandled webhook event: {} donationId={}", event, donationId);
        }

    }

}


