package com.techdemy.service;

import com.techdemy.dto.request.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(EmailRequest emailRequest) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
        mimeMessageHelper.setTo(emailRequest.getTo());
        mimeMessageHelper.setSubject(emailRequest.getSubject());
        mimeMessageHelper.setText(emailRequest.getMessage(), true);
        this.javaMailSender.send(message);
    }

}
