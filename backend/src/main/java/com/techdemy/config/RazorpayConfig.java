package com.techdemy.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorpayConfig {

    @Value("${razorpay.key_id}")
    private String razorpayKeyId;

    @Value("${razorpay.key_secret}")
    private String razorpaySecretKey;

    @Bean
    public RazorpayClient razorpayClient() throws RazorpayException {
        return new RazorpayClient( razorpayKeyId , razorpaySecretKey );
    }

}
