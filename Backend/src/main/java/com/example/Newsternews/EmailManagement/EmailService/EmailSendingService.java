package com.example.Newsternews.EmailManagement.EmailService;

import javax.mail.MessagingException;

public interface EmailSendingService {
    void sendEmail(String to, String subject, String body) throws MessagingException;
}
