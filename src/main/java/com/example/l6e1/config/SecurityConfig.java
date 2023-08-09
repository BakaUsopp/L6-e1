package com.example.l6e1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests((authz) -> authz
                                .requestMatchers(HttpMethod.GET,"/demo/**").hasAuthority("read")
                                .anyRequest()
                                .authenticated())
                               .csrf(Customizer.withDefaults())
                .build();
//                .disable();
//                        .requestMatchers("/test1").authenticated()
//                        .requestMatchers("/test2").hasAuthority("read")
//                        .anyRequest().authenticated()

//         http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        var user = new InMemoryUserDetailsManager();

        var user1 = User.withUsername("Usopp")
                .password(passwordEncoder().encode("1234"))
                .authorities("read").build();

        var user2 = User.withUsername("Zoro")
                .password(passwordEncoder().encode("6234"))
                .authorities("write").build();

        user.createUser(user1);
        user.createUser(user2);

        return user;

    }

    @Bean
    public PasswordEncoder passwordEncoder()  {

        return new BCryptPasswordEncoder();
    }



}
