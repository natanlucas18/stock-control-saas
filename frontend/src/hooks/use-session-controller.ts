import { useAuthStore } from "@/stores/auth-store"
import { useEffect } from "react"
import { useSilentRefresh } from "./use-silent-refresh"

export function useSessionController() {
  const expiresAt = useAuthStore((s) => s.expiresAt)
  const logout = useAuthStore((s) => s.logout)
  useSilentRefresh()

  useEffect(() => {
    if (!expiresAt) return

    const timeout = expiresAt - Date.now()

    if (timeout <= 0) {
      logout()
      return
    }

    const timer = setTimeout(() => {
      logout()
    }, timeout)

    return () => clearTimeout(timer)
  }, [expiresAt, logout])
}
