package com.ngocnb20.travel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Autowired
    MailProperties mailProperties;
    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername(mailProperties.getSenderEmail());
        javaMailSender.setPassword(mailProperties.getPassword());

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.allow8bitmime", "true");
        properties.setProperty("mail.smtps.allow8bitmime", "true");
        javaMailSender.setJavaMailProperties(properties);


        return javaMailSender;
    }
}
