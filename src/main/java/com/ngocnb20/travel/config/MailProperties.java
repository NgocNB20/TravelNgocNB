package com.ngocnb20.travel.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("app.mail")
public class MailProperties {

    private String senderName;
    private String senderEmail;
    private String password;
}
