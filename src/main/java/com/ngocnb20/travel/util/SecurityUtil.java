package com.ngocnb20.travel.util;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SecurityUtil {
    public static Long getIdCurrentUserLogin(){
        SecurityContext context = SecurityContextHolder.getContext();
        if(Objects.isNull(context.getAuthentication())){
            return null;
        }
//        UserDetails userDetails = (UserDetails) context.getAuthentication().getPrincipal();
//        Long id=Objects.nonNull(userDetails) ? Long.parseLong(userDetails.getUsername()):null;
//        return id;
        Long id;
        try {
            id = (Long) context.getAuthentication().getPrincipal();
            return id;
        }catch (Exception e){
            return 0L;
        }



    }
}
