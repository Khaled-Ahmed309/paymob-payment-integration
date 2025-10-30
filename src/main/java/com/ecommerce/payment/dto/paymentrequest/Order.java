package com.ecommerce.payment.dto.paymentrequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @JsonCreator)
@Getter
public class Order {

    @JsonProperty("id")
    private final Long orderId;
}
