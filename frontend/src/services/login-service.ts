'use server';

import { getApiUrl } from '@/lib/api-url';
import { LoginForm } from '@/types/login-schema';
import { ServerDTO } from '@/types/server-dto';
import { User } from '@/types/user-schema';

const localhost = getApiUrl();

export async function login(formData: LoginForm) {
  const response = await fetch(`${localhost}/auth/login`, {
    method: 'POST',
    headers: { 'content-type': 'application/json' },
    body: JSON.stringify(formData)
  });
  const responseData = await response.json();

  return responseData as ServerDTO<User>;
}
