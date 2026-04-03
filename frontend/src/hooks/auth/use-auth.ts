import { useAuthStore } from "@/stores/auth-store"

export function useAuth() {
  const user = useAuthStore((s) => s.user)
  const isAuthenticated = useAuthStore((s) => s.isAuthenticated)

  return {
    user,
    role: user?.role ?? "user",
    isAuthenticated
  }
}
