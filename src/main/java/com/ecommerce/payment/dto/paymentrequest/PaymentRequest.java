package com.ecommerce.payment.dto.paymentrequest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
@AllArgsConstructor
@Getter
public class PaymentRequest {
    @NotNull
    private final Long userId;
    @NotNull
    private final Long productId;
    @NotNull
    private final BigDecimal amount;
}
