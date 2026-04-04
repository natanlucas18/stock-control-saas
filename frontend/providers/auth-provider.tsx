"use client"

import { useSessionController } from "@/hooks/use-session-controller"
import { useAuthStore } from "@/stores/auth-store"
import { ReactNode, useEffect } from "react"

type AuthProviderProps = {
  children: ReactNode
}

export function AuthProvider({ children }: AuthProviderProps) {
  useSessionController()
  const hydrateSession = useAuthStore((s) => s.hydrateSession)

  useEffect(() => {
    hydrateSession()
  }, [hydrateSession])

  return <>{children}</>
}
