package com.ecommerce.payment.dto.payment.order.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor //it must it convert json to java object
public class PaymobOrderResponse {

    private final String id;
}
