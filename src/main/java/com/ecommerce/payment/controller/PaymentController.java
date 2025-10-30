package com.ecommerce.payment.controller;


import com.ecommerce.payment.dto.payment.PaymentRequest;
import com.ecommerce.payment.dto.payment.url.PaymentInitiation;
import com.ecommerce.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/createPayment")
    public ResponseEntity<PaymentInitiation> createPayment(@RequestBody PaymentRequest payment) throws Exception {
        return ResponseEntity.ok(paymentService.initiateCardPayment(payment));
    }
}
