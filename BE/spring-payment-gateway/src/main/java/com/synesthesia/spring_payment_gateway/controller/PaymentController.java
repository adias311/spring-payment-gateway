package com.synesthesia.spring_payment_gateway.controller;

import com.synesthesia.spring_payment_gateway.dto.payment.PaymentRequest;
import com.synesthesia.spring_payment_gateway.dto.payment.PaymentResponse;
import com.synesthesia.spring_payment_gateway.dto.payment.PaymentSummary;
import com.synesthesia.spring_payment_gateway.dto.payment.PaymentWebhook;
import com.synesthesia.spring_payment_gateway.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentResponse createPayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            return paymentService.createPayment(paymentRequest);
        } catch (Exception e) {
            log.error("Failed {}", e.getMessage());
            throw new RuntimeException("error");
        }
    }

    @GetMapping
    public Set<PaymentSummary> listPayments() {
        try {
            return paymentService.getAllPayments();
        } catch (Exception e) {
            log.error("Failed {}", e.getMessage());
            throw new RuntimeException("error");
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handlePaymentWebhook(
            @RequestBody PaymentWebhook webhook) {
        paymentService.handlePaymentWebhook(webhook);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/webhook/virtual-account")
    public ResponseEntity<String> handlePaymentWebhookVirtualAccount() {
        return ResponseEntity.ok("OK");
    }

}
