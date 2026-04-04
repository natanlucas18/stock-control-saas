import { loginService } from "@/services/login-service"
import { useAuthStore } from "@/stores/auth-store"
import { LoginForm } from "@/types/login-schema"
import { useMutation } from "@tanstack/react-query"

export function useLogin() {
  return useMutation({
    mutationFn: (data: LoginForm) => loginService(data),

    onSuccess: (data) => {
      useAuthStore.getState().setSession({
        user: {
            id: data.data.userId,
            name: data.data.userName,
            role: data.data.userRoles,
        },
        expiresIn: new Date(data.data.expiresAt).getTime()
      })
    }
  })
}
