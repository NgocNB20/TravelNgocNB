package com.ngocnb20.travel.config.auditable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorRef")
public class AuditConfiguration {
    @Bean
    public JpaAuditImpl auditorRef() {
        return new JpaAuditImpl();
    }

}
