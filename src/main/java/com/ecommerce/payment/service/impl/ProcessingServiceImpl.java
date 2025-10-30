package com.ecommerce.payment.service.impl;

import com.ecommerce.payment.dto.paymentrequest.Order;
import com.ecommerce.payment.dto.webhook.PaymobCallbackPayloadRequest;
import com.ecommerce.payment.exception.PayloadMissingException;
import com.ecommerce.payment.exception.PaymentNotFoundException;
import com.ecommerce.payment.dto.payment.PaymentSummary;
import com.ecommerce.payment.model.Payment;
import com.ecommerce.payment.repository.JdbcPaymentRepository;
import com.ecommerce.payment.service.ProcessingService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessingServiceImpl implements ProcessingService {
    private final JdbcPaymentRepository paymentRepository;

    @Override
    public void processPaymentCallback(PaymobCallbackPayloadRequest payloadRequest) {

        Boolean success = Boolean.valueOf(payloadRequest.getObj().getSuccess().toString());
        Order order = payloadRequest.getObj().getOrder();
        if (order == null)
            throw new PayloadMissingException("Payload missing 'order'");

        String paymentOrderId = order.getOrderId().toString();
        PaymentSummary payment = paymentRepository.findPaymentId(paymentOrderId);
        if (payment == null)
            throw new PaymentNotFoundException("Payment not found for orderId: " + paymentOrderId);

        if (success) {
            System.out.println("The status is: ACCEPTED and the status is changed");
            paymentRepository.updatePaymentStatus(payment.getPayment_id(), Payment.Status.ACCEPTED.name());
        } else {
            System.out.println("The status is: FAILED  and the status is changed");

            paymentRepository.updatePaymentStatus(payment.getPayment_id(), Payment.Status.FAILED.name());
        }
    }

}
