package com.example.Newsternews.EmailManagement.EmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.example.Newsternews.Keys.Keys;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSender implements EmailSendingService{

    private final JavaMailSender emailSender;

    public EmailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }


    @Override
    public void sendEmail(String to, String subject, String body) throws MessagingException {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(body, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom(Keys.EMAIL);
        this.emailSender.send(mimeMessage);

    }
}
