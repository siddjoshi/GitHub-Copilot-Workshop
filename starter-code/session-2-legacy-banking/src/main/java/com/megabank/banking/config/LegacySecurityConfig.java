package com.megabank.banking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Legacy Security Configuration for MegaBank Application
 * 
 * This configuration uses deprecated Spring Security patterns from version 4.x.
 * It needs to be modernized to Spring Security 6.0+ patterns.
 * 
 * LEGACY ISSUES:
 * - Uses deprecated WebSecurityConfigurerAdapter
 * - Old-style HTTP security configuration
 * - Outdated authentication patterns
 * - Missing modern security features
 * 
 * TODO: Use GitHub Copilot to modernize this security configuration:
 * 
 * COPILOT PROMPTS FOR MODERNIZATION:
 * - "@workspace modernize this Spring Security configuration to version 6.0+"
 * - "/fix replace deprecated WebSecurityConfigurerAdapter with modern patterns"
 * - "@workspace add JWT authentication with modern Spring Security"
 * - "@workspace implement OAuth2 Resource Server configuration"
 * - "@workspace add CORS configuration for microservices"
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class LegacySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    // TODO: Replace with modern JWT authentication filter
    // @Autowired
    // private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    // TODO: Add modern JWT token filter
    // @Autowired
    // private JwtRequestFilter jwtRequestFilter;

    /**
     * Legacy authentication configuration
     * TODO: Modernize with AuthenticationManager bean
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Legacy pattern - needs modernization
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Good practice - this can stay
        return new BCryptPasswordEncoder();
    }

    /**
     * Legacy HTTP Security Configuration
     * 
     * MODERNIZATION NEEDED:
     * - Replace deprecated methods
     * - Add modern JWT handling
     * - Implement proper CORS
     * - Add security headers
     * - Configure for microservices
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // Legacy configuration - needs complete modernization
        httpSecurity.csrf().disable()
                // TODO: Add proper CORS configuration
                .authorizeRequests()
                    .antMatchers("/api/auth/**").permitAll()
                    .antMatchers("/api/public/**").permitAll()
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers("/actuator/health").permitAll()
                    .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                    // TODO: Add modern authentication entry point
                    // .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // TODO: Add JWT filter
        // httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        
        // Legacy H2 console configuration - remove in production
        httpSecurity.headers().frameOptions().disable();
    }
}

/*
 * MODERNIZATION GUIDE FOR COPILOT:
 * 
 * 1. Replace WebSecurityConfigurerAdapter (DEPRECATED):
 *    - Create SecurityFilterChain bean
 *    - Use HttpSecurity DSL with lambda expressions
 *    - Configure authentication manager separately
 * 
 * 2. Modern JWT Implementation:
 *    - Add JWT decoder bean
 *    - Configure OAuth2 Resource Server
 *    - Implement custom JWT authentication converter
 *    - Add proper token validation
 * 
 * 3. Enhanced Security Features:
 *    - Content Security Policy (CSP)
 *    - X-Frame-Options, X-Content-Type-Options
 *    - HTTPS redirect configuration
 *    - Rate limiting integration
 * 
 * 4. Microservices Security:
 *    - Service-to-service authentication
 *    - API gateway integration
 *    - Distributed security context
 *    - Circuit breaker patterns
 * 
 * 5. Modern Authorization:
 *    - Method-level security with expressions
 *    - Role-based and permission-based access
 *    - Dynamic authorization policies
 *    - Integration with external identity providers
 * 
 * COPILOT COMMANDS TO USE:
 * - "@workspace create modern Spring Security 6 configuration"
 * - "/tests generate security test cases for authentication"
 * - "/optimize improve security configuration performance"
 * - "@workspace add OAuth2 and JWT support"
 */
