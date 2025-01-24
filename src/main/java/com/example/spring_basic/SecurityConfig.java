package com.example.spring_basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic(withDefaults());
        httpSecurity.authorizeHttpRequests(http ->{
            http.requestMatchers("/users").permitAll();
            http.anyRequest().authenticated();
        });
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails user= User.builder().username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER").build();

        UserDetails admin=User.builder().username("admin")
                .password(passwordEncoder().encode("password"))
                .roles("USER","ADMIN").build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
