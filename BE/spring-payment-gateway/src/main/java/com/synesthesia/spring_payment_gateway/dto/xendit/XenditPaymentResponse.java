package com.synesthesia.spring_payment_gateway.dto.xendit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.synesthesia.spring_payment_gateway.dto.xendit.channel.ChannelProperties;
import com.synesthesia.spring_payment_gateway.dto.xendit.channel.ChannelPropertiesDeserializer;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Data
public class XenditPaymentResponse {

    @JsonProperty("business_id")
    private String businessId;

    @JsonProperty("reference_id")
    private String referenceId;

    @JsonProperty("payment_request_id")
    private String paymentRequestId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("country")
    private String country;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("request_amount")
    private Long requestAmount;

    @JsonProperty("capture_method")
    private String captureMethod;

    @JsonProperty("channel_code")
    private String channelCode;

    @JsonProperty("channel_properties")
    private Object channelProperties;

    @JsonProperty("actions")
    private List<Action> actions;

    @JsonProperty("status")
    private String status;

    @JsonProperty("description")
    private String description;

    @JsonProperty("created")
    private OffsetDateTime created;

    @JsonProperty("updated")
    private OffsetDateTime updated;

}
