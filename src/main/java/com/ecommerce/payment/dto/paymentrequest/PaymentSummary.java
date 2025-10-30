package com.ecommerce.payment.dto.paymentrequest;

import lombok.*;

@Setter
@RequiredArgsConstructor
@Getter
public class PaymentSummary {
    private  Long id;
    private  String payment_id;
}
