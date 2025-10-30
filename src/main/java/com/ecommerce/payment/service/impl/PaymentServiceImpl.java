package com.ecommerce.payment.service.impl;

import com.ecommerce.payment.dto.payment.PaymentRequest;
import com.ecommerce.payment.dto.payment.auth.request.PaymentApiKeyRequest;
import com.ecommerce.payment.dto.payment.auth.response.PaymobTokenResponse;
import com.ecommerce.payment.dto.payment.config.PaymentProperties;
import com.ecommerce.payment.dto.payment.order.request.PaymobOrderRequest;
import com.ecommerce.payment.dto.payment.order.response.PaymobOrderResponse;
import com.ecommerce.payment.dto.payment.paymentKey.request.PaymobPaymentKeyRequest;
import com.ecommerce.payment.dto.payment.paymentKey.response.PaymobPaymentResponse;
import com.ecommerce.payment.dto.payment.url.PaymentInitiation;
import com.ecommerce.payment.dto.webhook.PaymobCallbackPayloadRequest;
import com.ecommerce.payment.exception.AuthTokenException;
import com.ecommerce.payment.exception.OrderIdException;
import com.ecommerce.payment.exception.TokenPaymentException;
import com.ecommerce.payment.exception.customexception.CustomException;
import com.ecommerce.payment.model.Payment;
import com.ecommerce.payment.repository.JdbcPaymentRepository;
import com.ecommerce.payment.service.PaymentService;
import com.ecommerce.payment.service.ProcessingService;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${PAYMOB_IFRAME_BASE_URL}")
    private String iframeBaseUrl;

    @Value("${PAYMOB_IFRAME_ID}")
    private String iframeId;

    private final JdbcPaymentRepository paymentRepository;
    private final ProcessingService processingService;
    private final PaymentProperties paymentProperties;
    private final RestClient restClient;




    private String getAuthToken() {

        PaymentApiKeyRequest bodyApiKey=new PaymentApiKeyRequest(paymentProperties.getApikey());
        System.out.println("API key is: "+bodyApiKey.getApi_key());
        ResponseEntity<PaymobTokenResponse> responseToken= restClient
                .post()
                .uri("/auth/tokens")
                .contentType(MediaType.APPLICATION_JSON)
                .body(bodyApiKey)
                .retrieve()
                .toEntity(PaymobTokenResponse.class);
        PaymobTokenResponse tokenResponse=responseToken.getBody();
        if (tokenResponse==null || tokenResponse.getToken()==null) {
            throw new AuthTokenException("Failed to get auth token from Paymob");

        }
        return tokenResponse.getToken();// return first token as string
    }

    private String createPaymobOrder(String authToken, BigDecimal price,Long id) throws Exception { // id of the payment in saved by database
        PaymobOrderRequest orderRequest=new PaymobOrderRequest(authToken,price.intValue());

        System.out.println("Second api: "+authToken);
        ObjectMapper objectMapper=new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(orderRequest));
        ResponseEntity<PaymobOrderResponse> response=restClient
                .post()
                .uri("/ecommerce/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderRequest)
                .retrieve()
                .toEntity(PaymobOrderResponse.class);

        PaymobOrderResponse orderResponse=response.getBody();

        if (orderResponse==null||orderResponse.getId()==null){
            throw new OrderIdException("Failed to get orderId from Paymob");
        }
        paymentRepository.updatePaymentId(orderResponse.getId(),id);
       return orderResponse.getId();

    }

    private String generatePaymentToken(String authToken, String orderId,BigDecimal amount) {


        PaymobPaymentKeyRequest bodyRequest=PaymobPaymentKeyRequest.builder()
                .auth_token(authToken)
                .amount_cents(amount.multiply(BigDecimal.valueOf(100)))
                .order_id(Integer.parseInt(orderId))
                .integration_id(Integer.parseInt(paymentProperties.getIntegrationid()))
                .build();

        ResponseEntity<PaymobPaymentResponse> response= restClient
                .post().uri("/acceptance/payment_keys")
                .contentType(MediaType.APPLICATION_JSON)
                .body(bodyRequest)
                .retrieve()
                .toEntity(PaymobPaymentResponse.class);

        PaymobPaymentResponse tokenResponse=response.getBody();
        if (tokenResponse==null||tokenResponse.getToken()==null){
            throw new TokenPaymentException("Failed to retrieve payment token from paymob");
        }
        return tokenResponse.getToken();

    }
    private String generatePaymentIframeUrl(String paymentToken){
        return iframeBaseUrl+iframeId+"?payment_token="+paymentToken;
    }

    @Transactional
    @Override
    public PaymentInitiation initiateCardPayment(PaymentRequest request) throws Exception {

        Payment payment=new Payment();
        payment.setUserId(request.getUserId());
        payment.setProductId(request.getProductId());
        payment.setPrice(request.getAmount());
        payment.setStatus(Payment.Status.PENDING);
        payment.setMethod(Payment.Method.PAYMOB);

        Payment newPayment= paymentRepository.save(payment);
        String authToken=getAuthToken();
        String orderId=createPaymobOrder(authToken,payment.getPrice(),newPayment.getId());
        String paymentToken= generatePaymentToken(authToken,orderId,payment.getPrice());

        String url=generatePaymentIframeUrl(paymentToken);
        return new PaymentInitiation(url);
    }

    @Transactional(rollbackFor = CustomException.class)
    @Override
    public void handlePaymentCallback(PaymobCallbackPayloadRequest payloadRequest, HttpServletRequest request) throws Exception {
        String receivedHmac=request.getParameter("hmac");
        if (receivedHmac==null){
            throw new CustomException("HMAC is missing in the request");
        }

        String concatenatedValues=concatenateValues(payloadRequest);
        System.out.println("Concatenated value: "+concatenatedValues);
        String calculatedHmac=calculateHmac(concatenatedValues,paymentProperties.getHmacSecretKey());
        System.out.println("Recived hmac: "+receivedHmac);
        System.out.println("Calculated hmac: "+calculatedHmac);
        if (!receivedHmac.equals(calculatedHmac)){
            throw new CustomException("Invalid HMAC signature");
        }
        processingService.processPaymentCallback(payloadRequest);
    }

    //using string.valueof as if the values are null there is no any exception
    private String concatenateValues(PaymobCallbackPayloadRequest payloadRequest) {
        String concatenated =
                String.valueOf(payloadRequest.getObj().getAmountCents()) +
                        String.valueOf(payloadRequest.getObj().getCreatedAt()) +
                        String.valueOf(payloadRequest.getObj().getCurrency()) +
                        String.valueOf(payloadRequest.getObj().getErrorOccured()) +
                        String.valueOf(payloadRequest.getObj().getHasParentTransaction()) +
                        String.valueOf(payloadRequest.getObj().getId()) +
                        String.valueOf(payloadRequest.getObj().getIntegrationId()) +
                        String.valueOf(payloadRequest.getObj().getIs3dSecure()) +
                        String.valueOf(payloadRequest.getObj().getIsAuth())+
                        String.valueOf(payloadRequest.getObj().getIsCapture()) +
                        String.valueOf(payloadRequest.getObj().getIsRefund()) +
                        String.valueOf(payloadRequest.getObj().getIsStandalonePayment()) +
                        String.valueOf(payloadRequest.getObj().getIsVoid()) +
                        (payloadRequest.getObj().getOrder() != null
                                ? String.valueOf(payloadRequest.getObj().getOrder().getOrderId())
                                : "") +
                        String.valueOf(payloadRequest.getObj().getOwner()) +
                        String.valueOf(payloadRequest.getObj().getPending()) +
                        (payloadRequest.getObj().getSourceData() != null
                                ? String.valueOf(payloadRequest.getObj().getSourceData().getPan()) +
                                String.valueOf(payloadRequest.getObj().getSourceData().getSubType()) +
                                String.valueOf(payloadRequest.getObj().getSourceData().getType())
                                : "") +
                        String.valueOf(payloadRequest.getObj().getSuccess());


        return concatenated;

    }


    private String calculateHmac(String data, String secretKey) {
        try {
            Mac sha512Hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            sha512Hmac.init(keySpec);
            byte[] macData = sha512Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            StringBuilder result = new StringBuilder();
            for (byte b : macData) {
                result.append(String.format("%02x", b));
            }
            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error calculating HMAC", e);
        }
    }

}
