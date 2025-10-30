package com.ecommerce.payment.dto.paymentrequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @JsonCreator)
@Getter
public class Obj {
    @JsonProperty("amount_cents")
    private final Long amountCents;
    @JsonProperty("created_at")
    private final String createdAt;
    @JsonProperty("currency")
    private final String currency;
    @JsonProperty("error_occured")
    private final Boolean errorOccured;
    @JsonProperty("has_parent_transaction")
    private final Boolean hasParentTransaction;
    @JsonProperty("id")
    private final Long id;
    @JsonProperty("integration_id")
    private final Long integrationId;
    @JsonProperty("is_3d_secure")
    private final Boolean is3dSecure;
    @JsonProperty("is_auth")
    private final Boolean isAuth;
    @JsonProperty("is_capture")
    private final Boolean isCapture;
    @JsonProperty("is_refund")
    private final Boolean isRefund;
    @JsonProperty("is_standalone_payment")
    private final Boolean isStandalonePayment;
    @JsonProperty("is_void")
    private final Boolean isVoid;
    @JsonProperty("order")
    private final Order order;
    @JsonProperty("owner")
    private final Long owner;
    @JsonProperty("pending")
    private final Boolean pending;
    @JsonProperty("source_data")
    private final SourceData sourceData;
    @JsonProperty("success")
    private final Boolean success;

}
