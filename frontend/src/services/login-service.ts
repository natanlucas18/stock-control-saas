'use server';

import { apiFetch } from '@/lib/api-client';
import { getApiUrl } from '@/lib/api-url';
import { LoginForm } from '@/types/login-schema';
import { ServerDTO } from '@/types/server-dto';
import { AuthResponse } from '@/types/user-schema';

const localhost = getApiUrl();

export async function loginService(formData: LoginForm) {
  return apiFetch<ServerDTO<AuthResponse>>(
    `${localhost}/auth/login`,
    {
      method: 'POST',
      body: JSON.stringify(formData)
    }
  )
}

