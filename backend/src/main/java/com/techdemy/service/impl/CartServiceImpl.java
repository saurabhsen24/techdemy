package com.techdemy.service.impl;

import com.techdemy.dto.response.CartDto;
import com.techdemy.entities.Cart;
import com.techdemy.entities.Course;
import com.techdemy.entities.User;
import com.techdemy.exception.ResourceNotFoundException;
import com.techdemy.repository.CartRepository;
import com.techdemy.repository.CourseRepository;
import com.techdemy.repository.UserRepository;
import com.techdemy.security.JwtHelper;
import com.techdemy.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void addToCart(Long courseId) {

        log.debug("Adding course {} to cart", courseId);

        Course course = courseRepository.findById(courseId).orElseThrow(() ->
                new ResourceNotFoundException("Course not found"));

        Long userId = getUserId();

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = Cart.builder()
                .user(user)
                .course(course)
                .build();

        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void removeCourseFromCart(Long courseId) {
        log.debug("Removing course {} from cart", courseId);

        Long userId = getUserId();
        cartRepository.deleteByUserIdAndCourseId(userId,courseId);
    }


    @Override
    public List<CartDto> getAllCartItems() {
        log.info("Fetching all cart items");

        Long userId = getUserId();
        return cartRepository.getAllCartItems(userId);

    }

    @Override
    public Integer getCartCount() {
        Long userId = getUserId();
        log.debug("Get cart count for user {}", userId);
        return cartRepository.getCartCount(userId);
    }

    private Long getUserId() {
        Long userId = Long.parseLong(JwtHelper.getCurrentLoggedInUserId());;
        return userId;
    }

}
