package com.synesthesia.spring_payment_gateway.dto.xendit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.synesthesia.spring_payment_gateway.dto.xendit.channel.ChannelProperties;
import lombok.Data;

@Data
public class XenditPaymentRequest {

    @JsonProperty("reference_id")
    private String referenceId;

    private String type;

    private String country;

    private String currency;

    @JsonProperty("request_amount")
    private Long requestAmount;

    @JsonProperty("capture_method")
    private String captureMethod;

    @JsonProperty("channel_code")
    private String channelCode;

    @JsonProperty("channel_properties")
    private ChannelProperties channelProperties;

    private String description;

}
