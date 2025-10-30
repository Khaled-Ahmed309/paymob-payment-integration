package com.ecommerce.payment.dto.payment;

import lombok.*;

@Setter
@RequiredArgsConstructor
@Getter
public class PaymentSummary {
    private  Long id;
    private  String payment_id;
}
