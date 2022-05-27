package com.ngocnb20.travel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailServiceImpl")
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource());
        http.headers().xssProtection();
        http.csrf().disable();
        http.authorizeRequests().
                antMatchers(HttpMethod.GET,"/agencies/**", "/css/**","/posts/**","/blogs/**","/places/**",
                        "/order-restaurant/**", "/order-hotel/**" ,"/order-tour/**","/authentication/**",
                        "/image/**", "/restaurants/**", "/image/blogs/**", "/file/**",
                        "/hotels/**", "/categories/**", "/reset-password","/members/**",
                        "/tours/**").permitAll().

                antMatchers(HttpMethod.POST, "/order-restaurant/**", "/order-hotel/**" ,"/order-tour/**").permitAll().

                antMatchers( "/api/login/**","/api/register","/api/exits-email","/send-mail","/order-tour/**").permitAll().

                antMatchers(HttpMethod.DELETE, "/agencies/**", "/css/**","/posts/**","/blogs/**","/places/**",
                        "/order-restaurant/**", "/order-hotel/**" ,"/order-tour/**","/authentication/**",
                        "/image/**", "/restaurants/**", "/image/blogs/**", "/file/**",
                        "/hotels/**", "/categories/**", "/reset-password","/members/**",
                        "/tours/**")
                .access("hasAnyRole('ROLE_ADMIN')").

                antMatchers(HttpMethod.PUT,"/blogs/view/**","/posts/view/**",
                        "/agencies/view/**","/hotels/view/**",
                        "/places/view/**","/posts/view/**",
                        "/restaurants/view/**","/tours/view/**",
                        "/blogs/like/**","/posts/like/**",
                        "/agencies/like/**","/hotels/like/**",
                        "/places/like/**","/posts/like/**",
                        "/restaurants/like/**","/tours/like/**" )
                .access("permitAll()").

                antMatchers(HttpMethod.PUT, "/agencies/**", "/css/**","/posts/**","/blogs/**","/places/**",
                        "/order-restaurant/**", "/order-hotel/**" ,"/order-tour/**","/authentication/**",
                        "/image/**", "/restaurants/**", "/image/blogs/**", "/file/**",
                        "/hotels/**", "/categories/**", "/reset-password","/members/**",
                        "/tours/**")
                .access("hasAnyRole('ROLE_ADMIN')").





                and().
                sessionManagement().
                sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().authorizeRequests().anyRequest().authenticated().
                and().apply(getSecurityConfig());

    }



    private JWTConfig getSecurityConfig() {
        return new JWTConfig();
    }




}

// http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//         http
//
//         .csrf().disable()
//         .authorizeRequests()
//         .antMatchers(HttpMethod.DELETE, "/api/v1/products/{productId}").hasRole(ADMIN.name()) // Admin should be able to delete
//         .antMatchers(HttpMethod.PUT, "/api/v1/products/{productId}").hasRole(ADMIN.name()) // Admin should be able to update
//         .antMatchers("/api/v1/products/add").hasAnyRole(ADMIN.name(), SUPERVISOR.name()) // Admin and Supervisor should be able to add product.
//         .antMatchers("/api/v1/products").hasAnyRole(ADMIN.name(), SUPERVISOR.name(), INTERN.name()) // All three users should be able to get all products.
//         .antMatchers("/api/v1/products{productId}").hasAnyRole(ADMIN.name(), SUPERVISOR.name(), INTERN.name()) // All three users should be able to get a product by id.
//         .anyRequest()
//         .authenticated()
//         .and()
//         .httpBasic();
