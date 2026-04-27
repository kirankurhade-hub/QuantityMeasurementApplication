package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for User entity operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by email.
     */
    Optional<User> findByEmail(String email);

    /**
     * Find user by email and provider (for local authentication).
     */
    Optional<User> findByEmailAndProvider(String email, String provider);

    /**
     * Find user by provider and providerId (for OAuth2 authentication).
     */
    Optional<User> findByProviderAndProviderId(String provider, String providerId);

    /**
     * Check if user exists by email.
     */
    boolean existsByEmail(String email);
}
