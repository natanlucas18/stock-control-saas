import { useAuthStore } from "@/stores/auth-store";

export function useSession() {
  return useAuthStore((state) => ({
    user: state.user,
    isAuthenticated: state.isAuthenticated
  }))
}
