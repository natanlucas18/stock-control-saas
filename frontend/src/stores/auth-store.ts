'use client';
import { Role } from '@/config/routes';
import { getApiUrl } from '@/lib/api-url';
import { AuthResponse } from '@/types/auth-response';
import { ServerDTO } from '@/types/server-dto';
import { create } from 'zustand';
import { createJSONStorage, persist } from 'zustand/middleware';

type User = {
  id: number;
  name: string;
  role: Role[];
};

type AuthState = {
  user: User | null;
  expiresAt: number | null;
  isAuthenticated: boolean;

  setSession: (data: { user: User; expiresAt: number }) => void;
  logout: () => void;
  hydrateSession: () => Promise<void>;
};

const localhost = getApiUrl();

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      user: null,
      expiresAt: null,
      isAuthenticated: false,

      setSession: ({ user, expiresAt }) =>
        set({
          user,
          expiresAt: Date.now() + expiresAt,
          isAuthenticated: true
        }),

      logout: () =>
        set({
          user: null,
          expiresAt: null,
          isAuthenticated: false
        }),

      hydrateSession: async () => {
        try {
          const res = await fetch(`${localhost}/auth/session`, {
            method: 'GET',
            credentials: 'include'
          });

          if (!res.ok) {
            set({
              user: null,
              expiresAt: null,
              isAuthenticated: false
            });
            return;
          }

          const data = await res.json() as ServerDTO<AuthResponse>;

          set({
            user: {
              id:data.data.userId,
              name: data.data.userName,
              role: data.data.userRoles
            },
            expiresAt: Date.now() + data.data.tokenExpiresAt,
            isAuthenticated: true
          });
        } catch (err) {
          console.error('Erro ao hidratar sessão:', err);

          set({
            user: null,
            expiresAt: null,
            isAuthenticated: false
          });
        }
      }
    }),
    {
      name: 'auth-session',
      storage: createJSONStorage(() => localStorage)
    }
  )
);
