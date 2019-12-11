package com.zhumingbei.techblog.service.impl;

import cn.hutool.core.util.StrUtil;
import com.zhumingbei.techblog.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class MailServiceImpl  implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendMail(String to, String subject, String content, boolean html, String filepath, String... cc) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setFrom(from);
        helper.setSubject(subject);
        helper.setText(content, html);
        if (cc != null && cc.length > 0) {
            helper.setCc(cc);
        }
        if (StrUtil.isNotEmpty(filepath)) {
            FileSystemResource file = new FileSystemResource(new File(filepath));
            String filename = filepath.substring(filepath.lastIndexOf(File.separator));
            helper.addAttachment(filename, file);
        }
        mailSender.send(message);
    }

    @Override
    public void sendSimpleMail(String to, String subject, String content, String... cc) throws MessagingException {
        sendMail(to, subject, content, false, "", cc);
    }
}
