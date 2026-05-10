import React, { useState } from 'react';
import { Card, CardContent, Typography, TextField, Button, Box, Alert } from '@mui/material';
import { authApi } from '../services/api';  // fixed: was AuthService (did not exist)
import { useAuth } from '../context/AuthContext';

/**
 * UC20 — Auth Page (standalone demo component).
 * Used as a teaching example; the full login flow uses LoginPage.jsx + AuthContext.
 *
 * Concepts: Authentication, Session Management, State & Controlled Inputs,
 *           Event Handlers in JSX, Conditional Rendering.
 */
export default function AuthPage() {
  const { user, login, logout } = useAuth();
  const [email, setEmail]       = useState('');
  const [password, setPassword] = useState('');
  const [error, setError]       = useState('');
  const [success, setSuccess]   = useState('');

  const handleLogin = async () => {
    setError(''); setSuccess('');
    const result = await login(email, password);
    if (result.success) setSuccess('Logged in successfully!');
    else setError(result.message || 'Login failed.');
  };

  const handleLogout = () => {
    logout();
    setSuccess('Logged out.');
  };

  return (
    <Card elevation={3}>
      <CardContent>
        <Typography variant="h5" fontWeight={700} gutterBottom>
          {user ? `Welcome, ${user.username}` : 'Authentication'}
        </Typography>

        {user ? (
          <Box>
            <Alert severity="success" sx={{ mb: 2 }}>Logged in as {user.email}</Alert>
            <Button variant="outlined" color="error" onClick={handleLogout}>Logout</Button>
          </Box>
        ) : (
          <Box>
            <TextField fullWidth label="Email" type="email" value={email}
              onChange={e => setEmail(e.target.value)} sx={{ mb: 2 }} />
            <TextField fullWidth label="Password" type="password" value={password}
              onChange={e => setPassword(e.target.value)} sx={{ mb: 2 }} />
            <Button variant="contained" fullWidth onClick={handleLogin}>Login</Button>
          </Box>
        )}

        {error   && <Alert severity="error"   sx={{ mt: 2 }}>{error}</Alert>}
        {success && <Alert severity="success" sx={{ mt: 2 }}>{success}</Alert>}
      </CardContent>
    </Card>
  );
}
