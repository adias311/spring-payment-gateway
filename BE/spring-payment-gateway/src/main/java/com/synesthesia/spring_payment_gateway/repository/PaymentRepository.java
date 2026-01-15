package com.synesthesia.spring_payment_gateway.repository;

import com.synesthesia.spring_payment_gateway.config.PaymentStatus;
import com.synesthesia.spring_payment_gateway.model.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentRepository {

    private final RedisTemplate<String, Payment> redisTemplate;

    @Value("${redis.payment.ttl}")
    private Duration paymentTtl;

    public void savePayment(Payment payment) {
        redisTemplate.opsForValue().set(
                paymentKey(payment.getDonationId()),
                payment,
                paymentTtl
        );
    }

    private String paymentKey(String donationId) {
        return "donation-payment:" + donationId;
    }

    public Stream<Payment> getAllPayment() {
        return redisTemplate.keys("donation-payment:*").stream()
                .map(key ->redisTemplate.opsForValue().get(key));
    }

    public void updatePaymentStatus(String donationId, PaymentStatus status, String reason) {
        String key = paymentKey(donationId);
        var payment = redisTemplate.opsForValue().get(key);
        if (payment == null) {
            log.warn("Payment not found: {}", donationId);
            return;
        }

        payment.setPaymentStatus(status);
        log.info("Payment {} donationId={} reason={}", status.name(), donationId, reason);

        redisTemplate.opsForValue().set(key, payment, paymentTtl);
    }

}
