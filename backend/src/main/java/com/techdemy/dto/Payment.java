package com.techdemy.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Payment {

    @NotBlank(message = "Order Id can't be blank")
    private String razorpayOrderId;

    @NotBlank(message = "Payment Id can't be blank")
    private String razorpayPaymentId;

    @NotBlank(message = "Signature can't be blank")
    private String razorpaySignature;

}
