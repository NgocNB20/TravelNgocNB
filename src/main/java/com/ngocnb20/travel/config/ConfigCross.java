package com.ngocnb20.travel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class ConfigCross {
//    @Bean
//    public WebMvcConfigurer crosConfigurer(){
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedMethods("GET","POST","PUT","DELETE")
//                        .allowedHeaders("*")
//                        .allowCredentials(false)
//                        .allowedOrigins("*");
//            }
//        };
//    }
}
