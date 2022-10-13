package com.techdemy.controller;

import com.techdemy.dto.response.CartDto;
import com.techdemy.dto.response.GenericResponse;
import com.techdemy.service.CartService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @ApiOperation(value = "Add to cart", response = GenericResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Added to cart"),
            @ApiResponse(code = 401, message = "You are not authenticated")
    })
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/{courseId}")
    public ResponseEntity<GenericResponse> addToCart(@PathVariable("courseId") Long courseId) {
        log.info("Got request to add course {} in cart", courseId);
        cartService.addToCart(courseId);
        return ResponseEntity.ok(GenericResponse.buildGenericResponse("Course added to cart"));
    }

    @ApiOperation(value = "Remove from cart", response = GenericResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Remove from cart"),
            @ApiResponse(code = 401, message = "You are not authenticated")
    })
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/{courseId}")
    public ResponseEntity<GenericResponse> removeFromCart(@PathVariable("courseId") Long courseId) {
        log.info("Got request to remove course {} from cart", courseId);
        cartService.removeCourseFromCart(courseId);
        return ResponseEntity.ok(GenericResponse.buildGenericResponse("Course removed from cart"));
    }

    @ApiOperation(value = "Gets all items from cart", response = CartDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fetches all cart items"),
            @ApiResponse(code = 401, message = "You are not authenticated")
    })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/all")
    public ResponseEntity<List<CartDto>> getAllCartItems() {
        log.info("Got request to fetch all cart items");
        return ResponseEntity.ok(cartService.getAllCartItems());
    }

}
