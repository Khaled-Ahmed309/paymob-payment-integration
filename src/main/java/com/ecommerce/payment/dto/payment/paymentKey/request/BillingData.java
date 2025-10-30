package com.ecommerce.payment.dto.payment.paymentKey.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillingData {

    private String apartment = "809";
    private String email = "khaledelsbaey5@gmail.com";
    private String floor = "4";
    private String first_name = "Khaled";
    private String last_name = "Elsbaey";
    private String street = "Tahrir Street";
    private String building = "12";
    private String phone_number = "201144740176";
    private String shipping_method = "PKG";
    private String city = "Cairo";
    private String country = "EG";
    private String state = "Cairo";

}
