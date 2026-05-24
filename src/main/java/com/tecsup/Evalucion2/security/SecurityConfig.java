package com.tecsup.Evalucion2.security;

import com.tecsup.Evalucion2.service.UsuarioDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsuarioDetailsServiceImpl usuarioDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .userDetailsService(usuarioDetailsService)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error").permitAll()

                        .requestMatchers(HttpMethod.GET, "/auditorias/**").hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.GET, "/clientes/**", "/categorias/**", "/productos/**", "/pedidos/**")
                        .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                        .requestMatchers(HttpMethod.POST, "/clientes/**", "/categorias/**", "/productos/**", "/pedidos/**")
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/clientes/**", "/categorias/**", "/productos/**")
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/clientes/**", "/categorias/**", "/productos/**", "/pedidos/**")
                        .hasAuthority("ROLE_ADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}