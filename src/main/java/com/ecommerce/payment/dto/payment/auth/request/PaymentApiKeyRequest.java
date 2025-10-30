package com.ecommerce.payment.dto.payment.auth.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentApiKeyRequest {
    private String api_key;
}
