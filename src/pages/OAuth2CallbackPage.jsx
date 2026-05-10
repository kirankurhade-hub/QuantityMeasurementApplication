import React, { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { Box, CircularProgress, Typography } from '@mui/material';
import { useAuth } from '../context/AuthContext';
import { authApi } from '../services/api';

/**
 * Handles the OAuth2 redirect from Spring Boot after Google login.
 * URL: http://localhost:3000/oauth2/callback?token=<jwt>
 * Saves token, fetches user profile, redirects to dashboard.
 */
export default function OAuth2CallbackPage() {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const { login } = useAuth();

  useEffect(() => {
    const token = searchParams.get('token');
    if (!token) {
      navigate('/login?error=oauth2_failed');
      return;
    }
    // Store token
    localStorage.setItem('qma_token', token);
    // Fetch user profile using the token
    authApi.me()
      .then(({ data }) => {
        if (data?.user) {
          localStorage.setItem('qma_user', JSON.stringify(data.user));
        } else {
          // Build minimal user from auth response
          const user = { email: data.email, username: data.name || data.email };
          localStorage.setItem('qma_user', JSON.stringify(user));
        }
        navigate('/dashboard');
      })
      .catch(() => {
        // Even if /me fails, token is stored — let dashboard handle it
        navigate('/dashboard');
      });
  }, [searchParams, navigate]);

  return (
    <Box sx={{ display:'flex', flexDirection:'column', alignItems:'center', justifyContent:'center', minHeight:'80vh', gap:2 }}>
      <CircularProgress size={48} />
      <Typography variant="h6" color="text.secondary">Completing Google Sign-In...</Typography>
    </Box>
  );
}
