package com.techdemy.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.techdemy.config.RazorpayPropertiesConfig;
import com.techdemy.dto.Payment;
import com.techdemy.dto.request.CheckoutRequest;
import com.techdemy.dto.response.CheckoutResponse;
import com.techdemy.entities.Checkout;
import com.techdemy.entities.User;
import com.techdemy.enums.TransactionType;
import com.techdemy.exception.PaymentException;
import com.techdemy.exception.ResourceNotFoundException;
import com.techdemy.repository.CheckoutRepository;
import com.techdemy.repository.UserRepository;
import com.techdemy.utils.Constants;
import com.techdemy.utils.Signature;
import com.techdemy.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SignatureException;
import java.util.UUID;

@Slf4j
@Service
public class CheckoutService {

    @Autowired
    private RazorpayClient razorpayClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RazorpayPropertiesConfig razorpayPropertiesConfig;

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Transactional
    public CheckoutResponse purchaseCourse(CheckoutRequest checkoutRequest) {
        log.debug("Checkout process initiated, total amt {}", checkoutRequest.getTotalAmount());

        Double totalAmount = checkoutRequest.getTotalAmount() * 100;
        JSONObject razorpayOrderRequest = createRazorpayOrderRequest( totalAmount );

        Order order = null;
        try {
            order = razorpayClient.orders.create(razorpayOrderRequest);
            User user = userRepository.findById(Utils.getCurrentLoggedInUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            Checkout checkout = Checkout.from(order, user, totalAmount.intValue());
            checkoutRepository.save(checkout);
        } catch (RazorpayException e) {
            log.warn("Payment is unsuccessful, {}", ExceptionUtils.getStackTrace(e));
            throw new PaymentException("Unsuccessful Payment");
        }

        return CheckoutResponse.from(order, razorpayPropertiesConfig.getKeyId());
    }

    @Transactional
    public void updateCheckout(Payment payment) {
        log.debug("Updating the txn status in DB for orderId: {} and paymentID: {}", payment.getRazorpayOrderId(),
                payment.getRazorpayPaymentId());

        Checkout checkout = checkoutRepository.findByOrderId(payment.getRazorpayOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Checkout order not found"));
        validateCheckoutProcess(payment);

        checkout.setTxnType(TransactionType.PAID);
        checkout.setPaymentId(payment.getRazorpayPaymentId());
        checkoutRepository.save(checkout);
    }

    private void validateCheckoutProcess( Payment payment ) {
        String generatedSignature = "";
        try {
            generatedSignature = Signature.calculateRFC2104HMAC(payment.getRazorpayOrderId() +
                    "|" + payment.getRazorpayPaymentId(), razorpayPropertiesConfig.getKeySecret());

            if( BooleanUtils.isFalse( generatedSignature.equals( payment.getRazorpaySignature() ) ) ) {
                throw new PaymentException("Payment Failed");
            }
        } catch (SignatureException e) {
            log.warn("Payment validation failed, {}", ExceptionUtils.getStackTrace(e));
            throw new PaymentException("Payment Failed");
        }
    }

    private JSONObject createRazorpayOrderRequest( Double totalAmount ) {
        JSONObject razorpayOrderRequest = new JSONObject();
        razorpayOrderRequest.put(Constants.AMOUNT, totalAmount.intValue());
        razorpayOrderRequest.put(Constants.CURRENCY, Constants.INR);
        razorpayOrderRequest.put(Constants.RECEIPT, Constants.TXN.concat(UUID.randomUUID().toString()));

        return razorpayOrderRequest;
    }

}
