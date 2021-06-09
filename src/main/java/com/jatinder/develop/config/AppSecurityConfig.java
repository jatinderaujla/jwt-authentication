package com.jatinder.develop.config;

import com.jatinder.develop.exception.JwtAuthenticationEntryPoint;
import com.jatinder.develop.filter.JWTSecurityFilter;
import com.jatinder.develop.service.AppUserDetailService;
import com.jatinder.develop.exception.JwtAuthenticationEntryPoint;
import com.jatinder.develop.filter.JWTSecurityFilter;
import com.jatinder.develop.service.AppUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/***
 * @author Jatinder
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AppUserDetailService appUserDetailsService;

    @Autowired
    private JWTSecurityFilter jwtSecurityFilter;

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    public AppSecurityConfig() {
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.appUserDetailsService).passwordEncoder(this.passwordEncoder());
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // enable CORS protection
        http.cors().configurationSource(corsConfigurationSource());

        // force to receive only HTTP request only
//		http.requiresChannel().anyRequest().requiresSecure();

        // disable Cross Site Request Forgery to prevent cross-site attack
        http.csrf().disable();

        // Content Security Policy to prevent XSS attack
        http.headers().contentSecurityPolicy(
                "default-src 'self'; script-src 'self' object-src 'none' report-uri /reports/csp-report/");

        http.headers().xssProtection();

        http.csrf().disable();

        http.authorizeRequests().antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // CORS configuration bean class
    private CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
                Arrays.asList("http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}