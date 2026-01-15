package com.synesthesia.spring_payment_gateway.resolver.payment;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PaymentProcessorResolver {

    private final Map<String, PaymentProcessor> processors;

    public PaymentProcessorResolver(List<PaymentProcessor> processorList) {
        this.processors = processorList.stream()
                .collect(Collectors.toMap(
                        p -> p.getMethod().toUpperCase(),
                        Function.identity()
                ));
    }

    public PaymentProcessor resolve(String method) {
        PaymentProcessor processor = processors.get(method.toUpperCase());
        if (processor == null) {
            throw new IllegalArgumentException("Unsupported payment method: " + method);
        }
        return processor;
    }
}
