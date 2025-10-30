package com.ecommerce.payment.dto.payment.auth.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@Getter
@Setter // it must to convert json from api to java object
@RequiredArgsConstructor
public class PaymobTokenResponse {
    private final String token;
}
