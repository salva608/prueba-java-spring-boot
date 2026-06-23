package com.talentboard.talentboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desactivamos CSRF temporalmente para permitir pruebas REST sin dolores de cabeza
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth
                        // 1. Rutas públicas para la interfaz web y el registro de usuarios
                        .requestMatchers("/login", "/auth/register", "/css/**", "/js/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()

                        // 2. Restricciones por rol para la Gestión de Vacantes
                        .requestMatchers(HttpMethod.POST, "/api/vacancies/**").hasAnyRole("ADMIN", "RECRUITER")
                        .requestMatchers(HttpMethod.PUT, "/api/vacancies/**").hasAnyRole("ADMIN", "RECRUITER")
                        .requestMatchers(HttpMethod.PATCH, "/api/vacancies/**").hasAnyRole("ADMIN", "RECRUITER")
                        .requestMatchers(HttpMethod.GET, "/api/vacancies/**").authenticated()

                        // 3. Restricciones por rol para Postulaciones (Applications)
                        .requestMatchers(HttpMethod.POST, "/api/applications").hasRole("CANDIDATE")
                        .requestMatchers(HttpMethod.PATCH, "/api/applications/**").hasAnyRole("ADMIN", "RECRUITER")
                        .requestMatchers(HttpMethod.GET, "/api/applications/candidate/**").hasAnyRole("ADMIN", "RECRUITER", "CANDIDATE")

                        // 4. Restricciones por rol para Entrevistas (Interviews)
                        .requestMatchers(HttpMethod.POST, "/api/interviews/**").hasAnyRole("ADMIN", "RECRUITER")

                        // Cualquier otra petición del sistema requiere autenticación previa
                        .anyRequest().authenticated()
                )

                // Configuración del formulario web nativo de Login para Thymeleaf
                .formLogin(form -> form
                        .loginPage("/login")                   // Apunta a la URL de tu controlador de vistas
                        .defaultSuccessUrl("/vacancies", true) // Redirección automática al entrar correctamente
                        .permitAll()
                )

                // Mantenemos soporte para autenticación básica (Basic Auth), ideal para pruebas rápidas en Postman
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}