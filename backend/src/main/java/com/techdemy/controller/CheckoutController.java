package com.techdemy.controller;

import com.techdemy.dto.Payment;
import com.techdemy.dto.request.CheckoutRequest;
import com.techdemy.dto.response.CheckoutResponse;
import com.techdemy.dto.response.GenericResponse;
import com.techdemy.service.CheckoutService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;


    @ApiOperation(value = "Checkout courses", response = CheckoutResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Checkout process initiated", response = CheckoutResponse.class),
            @ApiResponse( code = 401, message = "You are not authenticated" )
    })
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/purchase")
    public ResponseEntity<CheckoutResponse> purchaseCourse(@Valid @RequestBody CheckoutRequest checkoutRequest) {
        log.info("Got request to start payment process for total amt {}", checkoutRequest.getTotalAmount());
        return ResponseEntity.ok(checkoutService.purchaseCourse(checkoutRequest));
    }

    @PutMapping(value = "/purchase")
    public ResponseEntity<GenericResponse> updateCheckout(@Valid @RequestBody Payment payment) {
        checkoutService.updateCheckout(payment);
        return ResponseEntity.ok(GenericResponse.buildGenericResponse("Payment is Successful"));
    }

}
