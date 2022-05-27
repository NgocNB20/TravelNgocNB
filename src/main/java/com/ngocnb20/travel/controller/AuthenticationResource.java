package com.ngocnb20.travel.controller;

import com.ngocnb20.travel.constant.StatusRespData;
import com.ngocnb20.travel.model.dto.resp.BaseRespDto;
import com.ngocnb20.travel.util.TokenUtil;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.shaded.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/authentication",produces = "application/json;charset=UTF-8")
@CrossOrigin("*")
public class AuthenticationResource extends BaseController {

    @GetMapping(path = "/token")
    public ResponseEntity<BaseRespDto> verifyToken(@RequestParam String token){
        boolean check = false;
        if(TokenUtil.verifier(token)){
            JWSObject jwsObject  = TokenUtil.decodeToken(token);
            if (Objects.nonNull(jwsObject)){
                Map<String, Object> payload = jwsObject.getPayload().toJSONObject();
                JSONArray roles = (JSONArray) payload.get("role");

                for(int i=0; i< roles.size(); i++){
                     if(roles.get(i).toString().equals("ROLE_ADMIN")){
                         check = true;
                     }
                }
            }
        }
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,check)
        );
    }


    
}
