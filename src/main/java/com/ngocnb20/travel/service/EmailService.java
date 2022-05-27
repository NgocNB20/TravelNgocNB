package com.ngocnb20.travel.service;

import com.ngocnb20.travel.model.dto.EmailParamDto;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendMail(EmailParamDto emailDto) throws MessagingException, UnsupportedEncodingException;
}
