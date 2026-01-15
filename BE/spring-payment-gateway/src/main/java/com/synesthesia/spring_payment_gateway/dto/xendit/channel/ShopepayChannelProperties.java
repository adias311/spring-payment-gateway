package com.synesthesia.spring_payment_gateway.dto.xendit.channel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ShopepayChannelProperties implements ChannelProperties {

    @JsonProperty("failure_return_url")
    private String failureReturnUrl;

    @JsonProperty("success_return_url")
    private String successReturnUrl;

}
