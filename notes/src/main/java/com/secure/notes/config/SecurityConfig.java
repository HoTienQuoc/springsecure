package com.secure.notes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
        // http.authorizeHttpRequests(
        //     (requests)->requests.requestMatchers("/contact").permitAll()
        //                         .requestMatchers("/public/**").permitAll()
        //                         .requestMatchers("/admin").denyAll()
        //                         .requestMatchers("/admin/**").denyAll()
        //                         .anyRequest().authenticated()
        // );
        // // http.formLogin(withDefaults());
        // http.csrf(AbstractHttpConfigurer::disable);
        // http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // http.httpBasic(withDefaults());
        // return http.build();


        http.authorizeHttpRequests((requests)->requests.anyRequest().authenticated());
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(withDefaults());

        return http.build();
    }   

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager =
                new InMemoryUserDetailsManager();
        if (!manager.userExists("user1")) {
            manager.createUser(
                    User.withUsername("user1")
                            .password("{noop}password1")
                            .roles("USER")
                            .build()
            );
        }
        if (!manager.userExists("admin")) {
            manager.createUser(
                    User.withUsername("admin")
                            .password("{noop}adminPass")
                            .roles("ADMIN")
                            .build()
            );
        }
        return manager;
    }
}
