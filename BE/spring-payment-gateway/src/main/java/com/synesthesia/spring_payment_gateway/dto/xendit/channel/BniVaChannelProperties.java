package com.synesthesia.spring_payment_gateway.dto.xendit.channel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class BniVaChannelProperties implements ChannelProperties {

    @JsonProperty("expires_at")
    private OffsetDateTime expiresAt;

    @JsonProperty("display_name")
    private String displayName;

}
