package com.ecommerce.payment.controller;


import com.ecommerce.payment.dto.webhook.PaymobCallbackPayloadRequest;
import com.ecommerce.payment.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private final PaymentService paymentService;
    @Autowired
    private ObjectMapper objectMapper;
    @PostMapping("/paymob/callback")
    public ResponseEntity<String> handlePaymobCallback(@RequestBody PaymobCallbackPayloadRequest payLoadRequest, HttpServletRequest request) throws Exception {
           log.info("Payload is: {}",objectMapper.writeValueAsString(payLoadRequest));
            paymentService.handlePaymentCallback(payLoadRequest, request);
            return ResponseEntity.ok("Callback received");


    }
    @GetMapping("/paymob/response")
    public ResponseEntity<String> handlePaymentResponse(
            @RequestParam Map<String,String> queryParams){
        String success=queryParams.get("success");
        String message="Payment "+("true".equalsIgnoreCase(success)? "Successful!":"Failed");
        return ResponseEntity.ok(message);
    }
}
