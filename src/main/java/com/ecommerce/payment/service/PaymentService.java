package com.ecommerce.payment.service;

import com.ecommerce.payment.dto.webhook.PaymobCallbackPayloadRequest;
import com.ecommerce.payment.dto.payment.url.PaymentInitiation;
import com.ecommerce.payment.dto.payment.PaymentRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface PaymentService {
    PaymentInitiation initiateCardPayment(PaymentRequest request) throws Exception;
    void handlePaymentCallback(PaymobCallbackPayloadRequest payloadRequest, HttpServletRequest request) throws  Exception;
}
