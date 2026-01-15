package com.synesthesia.spring_payment_gateway.dto.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentWebhook {

    private String created;

    @JsonProperty("business_id")
    private String businessId;

    private String event;

    @JsonProperty("api_version")
    private String apiVersion;

    private DataPayload data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataPayload {

        @JsonProperty("payment_id")
        private String paymentId;

        @JsonProperty("business_id")
        private String businessId;

        @JsonProperty("reference_id")
        private String donationId;

        @JsonProperty("payment_request_id")
        private String paymentRequestId;

        private String status;

        @JsonProperty("request_amount")
        private int requestAmount;

        private String currency;

        @JsonProperty("channel_code")
        private String channelCode;

        private List<Capture> captures;

        @JsonProperty("payment_details")
        private PaymentDetails paymentDetails;

        private String created;
        private String updated;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Capture {
        @JsonProperty("capture_id")
        private String captureId;

        @JsonProperty("capture_timestamp")
        private String captureTimestamp;

        @JsonProperty("capture_amount")
        private int captureAmount;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PaymentDetails {
        @JsonProperty("authorization_data")
        private AuthorizationData authorizationData;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class AuthorizationData {
            @JsonProperty("authorization_code")
            private String authorizationCode;

            @JsonProperty("network_response_code")
            private String networkResponseCode;

            @JsonProperty("network_response_code_descriptor")
            private String networkResponseCodeDescriptor;

            @JsonProperty("retrieval_reference_number")
            private String retrievalReferenceNumber;
        }
    }
}
