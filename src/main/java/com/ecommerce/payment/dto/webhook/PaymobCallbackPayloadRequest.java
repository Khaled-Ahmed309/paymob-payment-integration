package com.ecommerce.payment.dto.webhook;

import com.ecommerce.payment.dto.paymentrequest.Obj;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @JsonCreator)
@Getter
public class PaymobCallbackPayloadRequest  {

    @JsonProperty("obj")
    private final Obj obj;




}
