package com.techdemy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Payment {

    @JsonProperty("razorpay_order_id")
    @NotBlank(message = "Order Id can't be blank")
    private String razorpayOrderId;

    @JsonProperty("razorpay_payment_id")
    @NotBlank(message = "Payment Id can't be blank")
    private String razorpayPaymentId;

    @JsonProperty("razorpay_signature")
    @NotBlank(message = "Signature can't be blank")
    private String razorpaySignature;

}
