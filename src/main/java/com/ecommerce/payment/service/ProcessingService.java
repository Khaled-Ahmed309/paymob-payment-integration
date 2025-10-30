package com.ecommerce.payment.service;


import com.ecommerce.payment.dto.webhook.PaymobCallbackPayloadRequest;

import java.util.Map;

public interface ProcessingService {
    void processPaymentCallback(PaymobCallbackPayloadRequest payloadRequest);
}
