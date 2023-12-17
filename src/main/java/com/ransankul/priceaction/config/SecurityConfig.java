package com.ransankul.priceaction.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.ransankul.priceaction.security.CustomUserDetailsService;
import com.ransankul.priceaction.security.JWTAuthFilter;
import com.ransankul.priceaction.security.JWTAuthenticationEntryPoint;




@Configuration
public class SecurityConfig implements AuthenticationProvider {


    @Autowired
    private JWTAuthFilter jwtauthFilter;
    
    @Autowired
    private JWTAuthenticationEntryPoint jwTauthEntryPoint;
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String[] PUBLIC_URL = {
        "/api/auth/**","/webhook/**"
    };


    @Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    
	    http.csrf(csrf-> csrf.disable()).cors(cors-> cors.disable())
        .authorizeHttpRequests(auth-> auth
        		.requestMatchers(PUBLIC_URL).permitAll()
        		.anyRequest().authenticated())
        .exceptionHandling(ex->ex.authenticationEntryPoint(jwTauthEntryPoint))
        .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtauthFilter,UsernamePasswordAuthenticationFilter.class);

        return http.build();
	}


	// Authenticate the requested user
    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        if (bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
        } else {
            throw new UsernameNotFoundException("incorrect password");
        }
    }




    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    } 

}

