package com.ecommerce.payment.dto.paymentrequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @JsonCreator)
@Getter
public class SourceData {

    @JsonProperty("pan")
    private final String pan;
    @JsonProperty("sub_type")
    private final String subType;
    @JsonProperty("type")
    private final String type;
}
