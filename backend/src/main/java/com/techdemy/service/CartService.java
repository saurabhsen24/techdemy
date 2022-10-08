package com.techdemy.service;

import com.techdemy.entities.Cart;

import java.util.List;

public interface CartService {

    void saveCart(Cart cart);

    Cart getCart(Long cartId);

    void updateCart(Cart cart);

    void deleteCart(Long cartId);

    List<Cart> getCarts();

}
