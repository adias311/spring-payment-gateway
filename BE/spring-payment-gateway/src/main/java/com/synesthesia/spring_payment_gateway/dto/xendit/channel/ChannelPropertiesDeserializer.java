package com.synesthesia.spring_payment_gateway.dto.xendit.channel;

import com.fasterxml.jackson.databind.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelPropertiesDeserializer {

    private final ObjectMapper mapper;

    /**
     * Deserialize JsonNode to ChannelProperties based on channelCode
     */
    public ChannelProperties deserialize(JsonNode node, String channelCode) {
        if (node == null || channelCode == null) {
            return null;
        }

        try {
            return switch (channelCode) {
                case "SHOPEEPAY" ->
                        mapper.treeToValue(node, ShopepayChannelProperties.class);
                case "BNI_VIRTUAL_ACCOUNT" ->
                        mapper.treeToValue(node, BniVaChannelProperties.class);
                case "DANA" ->
                        mapper.treeToValue(node, DanaChannelProperties.class);
                default -> throw new IllegalArgumentException(
                        "Unsupported channel_code: " + channelCode);
            };
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize ChannelProperties", e);
        }
    }
}


