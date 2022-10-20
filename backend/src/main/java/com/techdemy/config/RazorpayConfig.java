package com.techdemy.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorpayConfig {

    @Autowired
    private RazorpayPropertiesConfig razorpayPropertiesConfig;

    @Bean
    public RazorpayClient razorpayClient() throws RazorpayException {
        return new RazorpayClient( razorpayPropertiesConfig.getKeyId() , razorpayPropertiesConfig.getKeySecret() );
    }

}
