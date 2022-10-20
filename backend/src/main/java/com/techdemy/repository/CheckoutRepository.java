package com.techdemy.repository;

import com.techdemy.entities.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout,String> {

    Optional<Checkout> findByOrderId(String orderId);

}
