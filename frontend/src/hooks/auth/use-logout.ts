"use client"

import { useRouter } from "next/navigation"
import { useMutation, useQueryClient } from "@tanstack/react-query"
import { logoutRequest } from "@/services/logout-service"
import { useAuthStore } from "@/stores/auth-store"

export function useLogout() {
  const router = useRouter()
  const queryClient = useQueryClient()

  const clearAuth = useAuthStore((s) => s.logout)

  return useMutation({
    mutationFn: async () => {
      await logoutRequest()
    },

    onSettled: () => {
      clearAuth()

      queryClient.clear()
        router.replace("/login")
    }
  })
}
