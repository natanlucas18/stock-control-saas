import { getApiUrl } from "@/lib/api-url"
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

  setSession: (data: { user: User; expiresIn: number }) => void
  logout: () => void
  hydrateSession: () => Promise<void>
}

const localhost = getApiUrl();

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      user: null,
      expiresAt: null,
      isAuthenticated: false,

      setSession: ({ user, expiresIn }) =>
        set({
          user,
          expiresAt: Date.now() + expiresIn,
          isAuthenticated: true,
        }),

      logout: () =>
        set({
          user: null,
          expiresAt: null,
          isAuthenticated: false,
        }),

      hydrateSession: async () => {
        try {
          const res = await fetch(`${localhost}/api/users/me`, {
            credentials: "include", 
          })
          if (!res.ok) throw new Error("Sessão inválida")
          const data = await res.json()
          set({
            user: data.user,
            expiresAt: Date.now() + data.expiresIn,
            isAuthenticated: true,
          })
        } catch (err) {
          console.error(err)
          set({
            user: null,
            expiresAt: null,
            isAuthenticated: false,
          })
        }
      },
    }),
    {
      name: "auth-session",
      storage: createJSONStorage(() => sessionStorage),
    }
  )
)
