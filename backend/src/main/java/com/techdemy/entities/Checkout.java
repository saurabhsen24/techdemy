package com.techdemy.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.razorpay.Order;
import com.techdemy.dto.Payment;
import com.techdemy.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table( name = "checkout" )
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Checkout {

    @Id
    @Column( name = "order_id" )
    private String orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "user_id" )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private User user;

    @Column( name = "total_amt", nullable = false )
    private Integer totalAmount;

    @Column( name = "payment_id" )
    private String paymentId;

    @Column( name = "txn_type", nullable = false )
    private TransactionType txnType;

    @CreationTimestamp
    @Column( name = "created_on", nullable = false )
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column( name = "updated_on", nullable = false )
    private LocalDateTime updatedOn;

    public static Checkout from(Order order, User user, Integer totalAmount) {
        Checkout checkout = Checkout.builder()
                .orderId(order.get("id"))
                .paymentId(null)
                .totalAmount(totalAmount)
                .user(user)
                .txnType(TransactionType.CREATED)
                .build();

        return  checkout;
    }

}
