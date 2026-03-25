package com.banking.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchanges -> exchanges
                // Permitimos el acceso sin token a Eureka y Actuator por ahora
                .pathMatchers("/actuator/**", "/eureka/**").permitAll()
                // EXIGIMOS Token JWT para cualquier ruta de nuestra API de negocio
                .pathMatchers("/api/v1/**").authenticated()
                // El resto también protegido por seguridad
                .anyExchange().authenticated()
            )
            // Configuramos el Gateway como Resource Server que valida JWT
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        
        return http.build();
    }
}
