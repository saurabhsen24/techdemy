package com.techdemy.dto.request;

import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class CheckoutRequest {

    @Positive
    private Double totalAmount;

}
