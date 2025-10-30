package com.ecommerce.payment.dto.payment.paymentKey.request;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
//@NoArgsConstructor
@Getter
        /*
        * using @Builder.Default to initialize  it when creating constructor
        */
public class PaymobPaymentKeyRequest {

    private String auth_token;
    private BigDecimal amount_cents;
    @Builder.Default
    private int expiration = 3600;
    private long order_id;
    @Builder.Default
    private BillingData billing_data = new BillingData();
    @Builder.Default
    private String currency = "EGP";
    private int integration_id;


}
