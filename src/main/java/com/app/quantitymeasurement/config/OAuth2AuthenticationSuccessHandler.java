package com.app.quantitymeasurement.config;

import com.app.quantitymeasurement.dto.response.AuthResponse;
import com.app.quantitymeasurement.security.JwtUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * OAuth2 authentication success handler.
 * Generates JWT token after successful Google login and redirects with token.
 */
@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(OAuth2AuthenticationSuccessHandler.class);
    private final JwtUtils jwtUtils;

    public OAuth2AuthenticationSuccessHandler(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
                                        Authentication authentication) throws IOException, ServletException {
        
        log.info("OAuth2 authentication success handler triggered");
        
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        
        // Get user details from principal
        CustomOAuth2UserPrincipal principal = (CustomOAuth2UserPrincipal) oauth2User;
        
        // Generate JWT token
        String token = jwtUtils.generateToken(principal.getEmail());
        
        log.info("JWT token generated for OAuth2 user: {}", principal.getEmail());
        
        // Redirect to frontend index with token
        String redirectUrl = "http://localhost:3000/index.html?oauth_token=" + token + "&email=" + URLEncoder.encode(principal.getEmail(), "UTF-8");
        response.sendRedirect(redirectUrl);
    }
}
