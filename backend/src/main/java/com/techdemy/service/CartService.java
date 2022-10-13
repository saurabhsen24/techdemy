package com.techdemy.service;

import com.techdemy.dto.response.CartDto;
import com.techdemy.entities.Cart;

import java.util.List;

public interface CartService {

    void addToCart(Long courseId);

    void removeCourseFromCart(Long courseId);

    List<CartDto> getAllCartItems();

    Integer getCartCount();

}
