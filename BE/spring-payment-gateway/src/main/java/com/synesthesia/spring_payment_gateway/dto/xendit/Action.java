package com.synesthesia.spring_payment_gateway.dto.xendit;

import lombok.Data;

@Data
public class Action {

    private String type;

    private String descriptor;

    private String value;
}
