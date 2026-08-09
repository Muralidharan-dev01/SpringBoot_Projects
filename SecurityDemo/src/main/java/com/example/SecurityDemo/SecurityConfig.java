package com.example.SecurityDemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception
    {
        http.authorizeHttpRequests((requests)->requests.anyRequest().authenticated());
        http.sessionManagement(session->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // to disable cookie creation in every session(Which makes it as Statefull) -->to make
        // stateless we are using SessionManagement.

        //http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService()
    {
        //initialising users
        UserDetails user1= User.withUsername("user1")
                .password("{noop}password1")
                .roles("USER")
                .build();
        UserDetails admin= User.withUsername("admin")
                .password("{noop}password1")
                .roles("ADMIN")
                .build();

        //creating users in InMemory DB
        return new InMemoryUserDetailsManager(user1,admin);
    }
}
