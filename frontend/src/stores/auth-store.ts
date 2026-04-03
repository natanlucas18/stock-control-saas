import { create } from "zustand"
import { persist, createJSONStorage } from "zustand/middleware"

type User = {
  id: string
  name: string
  role: string[]
}

type AuthState = {
  user: User | null
  expiresAt: number | null
  isAuthenticated: boolean

  setSession: (data: {
    user: User
    expiresIn: number
  }) => void

  logout: () => void
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      user: null,
      tenant: null,
      expiresAt: null,
      isAuthenticated: false,

      setSession: ({ user, expiresIn }) =>
        set({
          user,
          expiresAt: Date.now() + expiresIn,
          isAuthenticated: true
        }),

      logout: () =>
        set({
          user: null,
          expiresAt: null,
          isAuthenticated: false
        })
    }),
    {
      name: "auth-session",
      storage: createJSONStorage(() => sessionStorage)
    }
  )
)
