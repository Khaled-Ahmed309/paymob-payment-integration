package com.ecommerce.payment.dto.payment.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter// must to work
@Component
@ConfigurationProperties(prefix = "paymob")
public class PaymentProperties {
    private String apikey;
    private String integrationid;
    private String iframeBaseUrl;
    private String iframeId;
    private String hmacSecretKey;
}
