package com.ngocnb20.travel.config;


import com.ngocnb20.travel.util.TokenUtil;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.shaded.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class JWTFillter extends GenericFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String bearerToken = request.getHeader("Authorization");
        System.out.println("bearerToken"+bearerToken);
        if (StringUtils.isNotBlank(bearerToken)){
            String token = bearerToken.replace("Bearer","");
            System.out.println(bearerToken);
            JWSObject jwsObject  = TokenUtil.decodeToken(token);
            if (Objects.nonNull(jwsObject)){
                Map<String, Object> payload = jwsObject.getPayload().toJSONObject();
                Long userId = Long.parseLong((String) payload.get("userId"));
                JSONArray roles = (JSONArray) payload.get("role");
                System.out.println("id:fillter"+userId);
                System.out.println(Arrays.asList(roles));

                List<GrantedAuthority> authorities =  roles.stream()
                        .map(role->new SimpleGrantedAuthority(role.toString()))
                        .collect(Collectors.toList());


                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(userId,null,authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);


            }
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }
}
