package com.zhumingbei.techblog.service;

import javax.mail.MessagingException;

public interface MailService {
    void sendMail(String to, String subject, String content, boolean html, String filepath, String... cc) throws MessagingException;
    void sendSimpleMail(String to, String subject, String content, String... cc) throws MessagingException;
}
