'use client';

import { apiFetch } from '@/lib/api-client';
import { getApiUrl } from '@/lib/api-url';
import { LoginForm } from '@/types/login-schema';
import { ServerDTO } from '@/types/server-dto';
import { AuthResponse } from '@/types/auth-response';

const localhost = getApiUrl();

export async function loginService(formData: LoginForm) {
  return apiFetch<ServerDTO<AuthResponse>>(
    `${localhost}/auth/login`, {
      method: 'POST',
      body: JSON.stringify(formData)
    }
  )
}



