package com.app.quantitymeasurement.dto.response;

/**
 * Response DTO for authentication (login/register/OAuth2).
 */
public class AuthResponse {

    private String token;
    private String type = "Bearer";
    private Long userId;
    private String email;
    private String fullName;
    private String provider;

    public AuthResponse() {
    }

    public AuthResponse(String token, String type, Long userId, String email, String fullName, String provider) {
        this.token = token;
        this.type = type;
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.provider = provider;
    }

    /**
     * Create a successful auth response.
     */
    public static AuthResponse success(String token, Long userId, String email, String fullName, String provider) {
        return new AuthResponse(token, "Bearer", userId, email, fullName, provider);
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
