import axios from 'axios';

// All traffic goes through the API Gateway at port 8080
const API_BASE = process.env.REACT_APP_API_URL || 'http://localhost:8080';

const api = axios.create({
  baseURL: API_BASE,
  headers: { 'Content-Type': 'application/json' },
});

// ── Attach JWT Bearer token to every request ──────────────────────────────────
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('qma_token');
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

// ── Auto-logout on 401 Unauthorized ──────────────────────────────────────────
api.interceptors.response.use(
  (res) => res,
  (err) => {
    if (err.response?.status === 401) {
      localStorage.removeItem('qma_token');
      localStorage.removeItem('qma_user');
      window.location.href = '/login';
    }
    return Promise.reject(err);
  }
);

// ── Auth Service  (port 8083 via Gateway) ─────────────────────────────────────
export const authApi = {
  register: (data) => api.post('/api/auth/register', data),
  login:    (data) => api.post('/api/auth/login', data),
  me:       ()     => api.get('/api/auth/me'),
};

// ── Conversion Service  (port 8082 via Gateway) ───────────────────────────────
export const conversionApi = {
  // GET /api/convert?value=&from=&to=&category=
  convert: (value, from, to, category) =>
    api.get('/api/convert', { params: { value, from, to, category } }),

  // GET /api/convert/history  — my conversions (JWT username extracted by backend)
  getHistory:   () => api.get('/api/convert/history'),

  // GET /api/convert/history/all  — all records (admin)
  getAllHistory: () => api.get('/api/convert/history/all'),
};

export default api;
