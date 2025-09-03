import { create } from 'zustand';
import http from '@/service/apiClient';

interface AuthState {
  isAuthenticated: boolean;
  user: User | null;
  isHydrated: boolean;
  isHydrating: boolean;
  login: (user: User) => void;
  logout: () => void;
  init: () => Promise<void>;
}

export const useAuthStore = create<AuthState>((set, get) => ({
  isAuthenticated: false,
  user: null,
  isHydrated: false,
  isHydrating: false,
  login: (user: User) => set({ isAuthenticated: true, user }),
  logout: () => set({ isAuthenticated: false, user: null }),
  init: async () => {
    const { isHydrated, isHydrating } = get();
    if (isHydrated || isHydrating) return;
    set({ isHydrating: true });
    try {
      const res = await http.get<{ user: User | null; status: number; message?: string }>(
        '/api/me',
      );
      if (res.data.status === 200 && res.data.user) {
        set({ user: res.data.user, isAuthenticated: true });
      } else {
        set({ user: null, isAuthenticated: false });
      }
    } catch {
      set({ user: null, isAuthenticated: false });
    } finally {
      set({ isHydrated: true, isHydrating: false });
    }
  },
}));

export const useAuth = () => {
  const { isAuthenticated, user, login, logout, init, isHydrated, isHydrating } = useAuthStore();
  return { isAuthenticated, user, login, logout, init, isHydrated, isHydrating };
};
