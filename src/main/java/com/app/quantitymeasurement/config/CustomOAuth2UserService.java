package com.app.quantitymeasurement.config;

import com.app.quantitymeasurement.entity.User;
import com.app.quantitymeasurement.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Custom OAuth2 user service for Google login.
 * Handles user extraction and auto-registration from Google OAuth2.
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final Logger log = LoggerFactory.getLogger(CustomOAuth2UserService.class);
    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Loading OAuth2 user from Google");
        
        OAuth2User oauth2User = super.loadUser(userRequest);
        String email = oauth2User.getAttribute("email");
        String providerId = oauth2User.getAttribute("sub");
        String fullName = oauth2User.getAttribute("name");

        log.info("Google user email: {}, providerId: {}", email, providerId);

        // Check if user exists with this provider
        Optional<User> existingUser = userRepository.findByProviderAndProviderId("google", providerId);
        
        User user;
        if (existingUser.isPresent()) {
            user = existingUser.get();
            log.info("Existing Google user found: {}", email);
        } else {
            // Auto-register new user
            log.info("Registering new Google user: {}", email);
            user = new User();
            user.setEmail(email);
            user.setFullName(fullName != null ? fullName : email);
            user.setProvider("google");
            user.setProviderId(providerId);
            user.setPassword(""); // No password for OAuth2 users
            user.setEnabled(true);
            
            user = userRepository.save(user);
            log.info("New Google user registered with ID: {}", user.getId());
        }

        return new CustomOAuth2UserPrincipal(user, oauth2User.getAttributes());
    }
}
