package com.techdemy.service.impl;

import com.techdemy.dto.request.EmailRequest;
import com.techdemy.entities.ResetToken;
import com.techdemy.entities.User;
import com.techdemy.exception.BadRequestException;
import com.techdemy.repository.ResetTokenRepository;
import com.techdemy.service.EmailService;
import com.techdemy.service.ResetTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ResetTokenServiceImpl implements ResetTokenService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ResetTokenRepository resetTokenRepository;

    @Override
    public void generateToken(User user) {

        log.debug("Generates token and saves in db for user {}", user.getUserName());

        String token = UUID.randomUUID().toString();

        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes( 5 );

        deleteResetTokenIfExist( user.getUserId() );

        ResetToken resetToken = ResetToken.builder()
                .token(token)
                .user(user)
                .expiredAt(expiresAt)
                .build();

        resetTokenRepository.save(resetToken);

        String message = "<h3> Your Reset Token: " + token + " </h3>";

        EmailRequest emailRequest = EmailRequest.builder().to(user.getEmail().trim())
                .message(message).subject("Token Verification").build();

        sendMail(emailRequest);

    }

    @Override
    public void validateResetToken(String token) {
        ResetToken resetToken = resetTokenRepository.findByToken( token )
                .orElseThrow(() -> new BadRequestException("Invalid token"));

        if(resetToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Token expired, Please try with new token");
        }

    }

    @Override
    @Async
    public void deleteToken( String token ) {
        resetTokenRepository.deleteByToken(token);
    }

    @Async
    private void sendMail( EmailRequest emailRequest ) {
        log.debug("Sending message {} through mail for user {}", emailRequest.getMessage(), emailRequest.getTo());
        try {
            emailService.sendMail(emailRequest);
            log.debug("Mail sent successfully to the user {}", emailRequest.getTo());
        } catch (MessagingException e) {
            log.warn("Email not sent, {}", ExceptionUtils.getStackTrace(e));
        }
    }

    private void deleteResetTokenIfExist( Long userId ) {
        ResetToken resetToken = resetTokenRepository.findById(userId).orElse(null);

        if(Objects.isNull(resetToken)) {
            return;
        }

        log.debug("Deleting the existing reset token for user {}", userId);
        resetTokenRepository.delete(resetToken);

    }

}
