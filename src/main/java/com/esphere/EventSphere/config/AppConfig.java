package com.esphere.EventSphere.config;

import com.esphere.EventSphere.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AppConfig {
//    @Autowired
//    private AuthEntryPointJwt unauthorizedHandler;
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{


        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(Authorize->Authorize.requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll());
        http.addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class);
        http.csrf(csrf ->csrf.disable());
//        http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedhandler));
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions
                        .sameOrigin()
                )
        );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
    //    public AuthenticationProvider authenticationProvider(){
    //        DaoAuthenticationProvider provider =  new DaoAuthenticationProvider();
    //        provider.setPasswordEncoder(new BCryptPasswordEncoder());
    //        provider.setUserDetailsService(customUserDetailsService);
    //        return provider;
    //    }

    private CorsConfigurationSource corsConfigurationSource() {
        return null;
    }
}
