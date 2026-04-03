"use client"

import { useSessionController } from "@/hooks/use-session-controller"
import { ReactNode } from "react"

type AuthProviderProps = {
  children: ReactNode
}

export function AuthProvider({ children }: AuthProviderProps) {
  useSessionController()

  return <>{children}</>
}
