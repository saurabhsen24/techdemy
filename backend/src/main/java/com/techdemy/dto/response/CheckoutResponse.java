package com.techdemy.dto.response;

import com.razorpay.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutResponse {

    private String orderId;
    private String secretKey;


    public static CheckoutResponse from(Order order, String razorpaySecret) {
        CheckoutResponse checkoutResponse = CheckoutResponse.builder()
                .orderId(order.get("id"))
                .secretKey(razorpaySecret)
                .build();
        return checkoutResponse;
    }

}
