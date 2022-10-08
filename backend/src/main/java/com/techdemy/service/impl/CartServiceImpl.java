package com.techdemy.service.impl;

import com.techdemy.entities.Cart;
import com.techdemy.exception.ResourceNotFoundException;
import com.techdemy.repository.CartRepository;
import com.techdemy.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public Cart getCart(Long cartId) {
        Cart cart = cartRepository.findById( cartId ).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        return cart;
    }

    @Override
    public void updateCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public void deleteCart(Long cartId) {
        cartRepository.deleteById( cartId );
    }

    @Override
    public List<Cart> getCarts() {
        return cartRepository.findAll();
    }
}
