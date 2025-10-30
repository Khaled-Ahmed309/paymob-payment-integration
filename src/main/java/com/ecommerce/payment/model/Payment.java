package com.ecommerce.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Payment {


    private Long id;
    private String payment_id;
    private Long userId;
    private Long productId;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
    private Method method;

    public enum Status {
        PENDING,
        ACCEPTED,
        FAILED
    }

    public enum Method {
        PAYMOB
    }
}
