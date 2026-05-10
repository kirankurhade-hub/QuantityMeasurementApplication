import React, { createContext, useContext, useState, useCallback } from 'react';
import { authApi } from '../services/api';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser]     = useState(() => {
    try { return JSON.parse(localStorage.getItem('qma_user')); } catch { return null; }
  });
  const [loading, setLoading] = useState(false);
  const [error,   setError]   = useState('');

  const register = useCallback(async (username, email, password) => {
    setLoading(true); setError('');
    try {
      const { data } = await authApi.register({ username, email, password });
      if (data.success) {
        localStorage.setItem('qma_token', data.token);
        localStorage.setItem('qma_user',  JSON.stringify(data.user));
        setUser(data.user);
        return { success: true };
      }
      setError(data.message);
      return { success: false, message: data.message };
    } catch (e) {
      const msg = e.response?.data?.message || 'Registration failed';
      setError(msg);
      return { success: false, message: msg };
    } finally { setLoading(false); }
  }, []);

  const login = useCallback(async (email, password) => {
    setLoading(true); setError('');
    try {
      const { data } = await authApi.login({ email, password });
      if (data.success) {
        localStorage.setItem('qma_token', data.token);
        localStorage.setItem('qma_user',  JSON.stringify(data.user));
        setUser(data.user);
        return { success: true };
      }
      setError(data.message);
      return { success: false, message: data.message };
    } catch (e) {
      const msg = e.response?.data?.message || 'Login failed';
      setError(msg);
      return { success: false, message: msg };
    } finally { setLoading(false); }
  }, []);

  const logout = useCallback(() => {
    localStorage.removeItem('qma_token');
    localStorage.removeItem('qma_user');
    setUser(null);
  }, []);

  return (
    <AuthContext.Provider value={{ user, loading, error, register, login, logout, isLoggedIn: !!user }}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => useContext(AuthContext);
export default AuthContext;
