package com.synesthesia.spring_payment_gateway.helper.payment;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synesthesia.spring_payment_gateway.config.PaymentStatus;
import com.synesthesia.spring_payment_gateway.dto.payment.PaymentRequest;
import com.synesthesia.spring_payment_gateway.dto.xendit.XenditPaymentResponse;
import com.synesthesia.spring_payment_gateway.dto.xendit.channel.ChannelProperties;
import com.synesthesia.spring_payment_gateway.dto.xendit.channel.ChannelPropertiesDeserializer;
import com.synesthesia.spring_payment_gateway.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Random;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentHelper {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private final ChannelPropertiesDeserializer channelPropertiesDeserializer;
    private final ObjectMapper objectMapper;

    public Payment mapToPayment(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setDonationId(UUID.randomUUID().toString());
        payment.setDonatorName(request.getDonatorName());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
        return payment;
    }

    public String generateVirtualAccountNumber(String prefix, int length) {
        if (prefix == null) prefix = "";

        int randomLength = length - prefix.length();
        if (randomLength < 1 || randomLength > 8 || length > 32) {
            throw new IllegalArgumentException("Invalid VA length");
        }

        int min = 1;
        int max = (int) Math.pow(10, randomLength) - 1;
        int randomNumber = SECURE_RANDOM.nextInt(max - min + 1) + min;

        return prefix + String.format("%0" + randomLength + "d", randomNumber);
    }

    public String buildSuccessUrl(String successBaseUrl, String donationId) {
        return UriComponentsBuilder
                .fromUriString(successBaseUrl)
                .queryParam("donationPaymentId", donationId)
                .build()
                .toUriString();
    }

    public void deserializeChannelProperties(XenditPaymentResponse response) {
        if (response == null) return;
        String channelCode = response.getChannelCode();
        JsonNode channelNode = objectMapper.valueToTree(response.getChannelProperties());
        ChannelProperties props = channelPropertiesDeserializer.deserialize(channelNode, channelCode);
        response.setChannelProperties(props);
    }

}

