package com.ngocnb20.travel.controller;

import com.ngocnb20.travel.constant.StatusRespData;
import com.ngocnb20.travel.model.dto.MemberDto;
import com.ngocnb20.travel.model.dto.resp.BaseRespDto;
import com.ngocnb20.travel.service.MemberService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;

@RestController
@RequestMapping(path = "/reset-password",produces = "application/json;charset=UTF-8")
@CrossOrigin("*")
public class ResetPasswordController {
    @Autowired
    MemberService memberService;

    @PostMapping
    public ResponseEntity<?> RestPassword(@RequestBody @Valid MemberDto memberDto) throws ParseException, JOSEException {
       if(memberService.getByEmail(memberDto.getEmail())) {
             return ResponseEntity.ok(true);
       }
        return ResponseEntity.ok(false);
    }

    @PostMapping("/check")
    public  ResponseEntity<?> check(@RequestBody String token){
     return null;

    }

}
