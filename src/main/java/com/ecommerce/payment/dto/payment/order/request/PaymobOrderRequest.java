package com.ecommerce.payment.dto.payment.order.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Setter
@Getter // To make jakson convert java code to json
@NoArgsConstructor
public class PaymobOrderRequest {
    private  String auth_token;
    private String delivery_needed;
    private int amount_cents;
    private String currency;
    private List<Map<String,Object>> items;

    public PaymobOrderRequest(String auth_token,int amount_cents){
        this.auth_token=auth_token;
        this.delivery_needed="false";
        this.amount_cents=amount_cents;
        this.currency="EGP";
        this.items=new ArrayList<>();
    }
}
