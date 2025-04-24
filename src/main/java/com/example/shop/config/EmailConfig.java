package com.example.shop.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.security.Key;
import java.util.Properties;

@Configuration
@Data
public class EmailConfig {
    @Value("${mail.host}")
    private String host;
    @Value("${mail.password}")
    private String password;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.auth}")
    private String auth;
    @Value("${mail.enable}")
    private String enable;
    @Value("${mail.port}")
    private Integer port;
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", enable);
//        props.put("mail.debug", "true");
        return mailSender;
    }



}
