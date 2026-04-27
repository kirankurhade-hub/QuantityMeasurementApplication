import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { motion } from 'framer-motion';
import { Mail, Lock, ArrowRight, Eye, EyeOff } from 'lucide-react';
import { useAuth } from '../../hooks/useAuth';
import Button from '../../components/ui/Button';
import Input from '../../components/ui/Input';
import Toast from '../../components/ui/Toast';

export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const [toast, setToast] = useState({ visible: false, message: '', type: 'info' });
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!email || !password) {
      setToast({ visible: true, message: 'Please fill in all fields', type: 'error' });
      return;
    }

    setLoading(true);
    try {
      await login(email, password);
      setToast({ visible: true, message: 'Login successful!', type: 'success' });
      setTimeout(() => navigate('/dashboard'), 500);
    } catch (error) {
      const message = error.response?.data?.message || 'Login failed. Please check your credentials.';
      setToast({ visible: true, message, type: 'error' });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <Toast
        message={toast.message}
        type={toast.type}
        visible={toast.visible}
        onClose={() => setToast((prev) => ({ ...prev, visible: false }))}
      />

      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ delay: 0.2, duration: 0.5 }}
      >
        <h1 className="text-3xl font-bold text-white mb-2">Welcome back</h1>
        <p className="text-gray-400 mb-8 text-sm">Sign in to your account to continue</p>
      </motion.div>

      <motion.form
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ delay: 0.3, duration: 0.5 }}
        onSubmit={handleSubmit}
        className="space-y-5"
      >
        <Input
          label="Email address"
          type="email"
          placeholder="you@example.com"
          icon={Mail}
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <div className="space-y-1.5">
          <label className="block text-sm font-medium text-gray-300">Password</label>
          <div className="relative">
            <div className="absolute inset-y-0 left-0 pl-3.5 flex items-center pointer-events-none">
              <Lock className="w-4 h-4 text-gray-500" />
            </div>
            <input
              type={showPassword ? 'text' : 'password'}
              placeholder="Enter your password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full bg-white/[0.03] border border-white/[0.06] rounded-xl pl-10 pr-10 py-3 text-white placeholder-gray-500 transition-all duration-200 focus:outline-none input-glow focus:border-teal-500/50"
            />
            <button
              type="button"
              onClick={() => setShowPassword(!showPassword)}
              className="absolute inset-y-0 right-0 pr-3.5 flex items-center text-gray-500 hover:text-gray-300 transition-colors"
            >
              {showPassword ? <EyeOff className="w-4 h-4" /> : <Eye className="w-4 h-4" />}
            </button>
          </div>
        </div>

        <Button
          type="submit"
          loading={loading}
          className="w-full"
          size="lg"
          icon={ArrowRight}
        >
          Sign In
        </Button>
      </motion.form>

      <motion.p
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 0.5, duration: 0.5 }}
        className="mt-8 text-center text-sm text-gray-500"
      >
        Don't have an account?{' '}
        <Link
          to="/register"
          className="text-teal-400 hover:text-teal-300 font-medium transition-colors"
        >
          Create one
        </Link>
      </motion.p>
    </div>
  );
}
