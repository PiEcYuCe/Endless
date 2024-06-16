package com.java5.assignment.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendHtmlMail(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        javaMailSender.send(mimeMessage);
    }

    public void sendCancelOrder(long orderID, String to) throws MessagingException {
        String subject = "Order Cancellation Confirmation";
        String htmlBody = "<p>Dear Customer,</p>"
                + "<p>We regret to inform you that your order with order ID <strong>" + orderID + "</strong> has been canceled successfully.</p>"
                + "<p>If you have any questions or concerns regarding this cancellation, please feel free to contact our customer support team.</p>"
                + "<p>Thank you for your understanding.</p>"
                + "<p>Best regards,<br> [Your Company Name]</p>";

        sendHtmlMail(to, subject, htmlBody);
    }


}
