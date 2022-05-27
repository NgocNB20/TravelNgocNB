package com.ngocnb20.travel.controller;

import com.ngocnb20.travel.model.dto.EmailParamDto;
import com.ngocnb20.travel.model.dto.param.EmailReceiveDto;
import com.ngocnb20.travel.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(path = "/send-mail",produces = "application/json;charset=UTF-8")
@CrossOrigin("*")
public class SendMailController {
    @Autowired
    EmailService emailService;
    @PutMapping
    public void SendMailRestaurant(@ModelAttribute EmailReceiveDto emailReceive){

        EmailParamDto emailParamDto = EmailParamDto.builder()
                .emailTo(emailReceive.getEmail())
                .subject(emailReceive.getSubject())
                .content(emailReceive.getContent())
                .build();
        try {
            emailService.sendMail(emailParamDto);
        }catch (Exception e){
            System.out.println("send mail false");
        }



    }

}
