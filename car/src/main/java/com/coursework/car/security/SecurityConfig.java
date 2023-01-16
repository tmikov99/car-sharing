package com.coursework.car.security;

import com.coursework.car.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final List<String> ORIGINS = Collections.singletonList("http://localhost:3000/");
    private static final List<String> METHODS = Arrays.asList("POST", "PUT", "DELETE", "GET");
    private static final List<String> HEADERS = Collections.singletonList("*");

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(email -> userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                format("User: %s, not found", email)
                        )
                )).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = http.cors().and().csrf().disable();
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                )
                .and();
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/user/**").permitAll()
                .antMatchers(HttpMethod.POST, "/user/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/user/**").permitAll()
                .antMatchers(HttpMethod.GET, "/offer/**").permitAll()
                .antMatchers(HttpMethod.POST, "/offer/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/offer/**").permitAll()
                .antMatchers(HttpMethod.GET, "/reservation/**").permitAll()
                .antMatchers(HttpMethod.POST, "/reservation/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/reservation/**").permitAll()
                .antMatchers(HttpMethod.GET, "/message/**").permitAll()
                .antMatchers(HttpMethod.POST, "/message/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/message/**").permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(
                jwtTokenFilter,
                UsernamePasswordAuthenticationFilter.class
        );
    }

    @Override @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(ORIGINS);
        config.setAllowedMethods(METHODS);
        config.setAllowedHeaders(HEADERS);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}