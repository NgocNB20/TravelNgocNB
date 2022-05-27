package com.ngocnb20.travel.service.impl;

import com.ngocnb20.travel.config.MailProperties;
import com.ngocnb20.travel.model.dto.EmailParamDto;
import com.ngocnb20.travel.service.EmailService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;


@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    MailProperties mailProperties;

    @Override
    public void sendMail(EmailParamDto emailDto) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

        messageHelper.setFrom(mailProperties.getSenderEmail(),
                mailProperties.getSenderName());

        messageHelper.setTo(emailDto.getEmailTo());
        messageHelper.setSubject(emailDto.getSubject());
        messageHelper.setText(emailDto.getContent(),true);

        if (ArrayUtils.isNotEmpty(emailDto.getCcList())){
            messageHelper.setCc(emailDto.getCcList());
        }
        if (ArrayUtils.isNotEmpty(emailDto.getBccList())){
            messageHelper.setCc(emailDto.getBccList());
        }

        javaMailSender.send(mimeMessage);


    }
}
