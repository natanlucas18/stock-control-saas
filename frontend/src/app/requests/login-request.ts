'use server';

import { ServerDTO } from '@/types/server-dto';
import { AuthData, LoginForm } from '@/types/types';

export async function login(formData: LoginForm) {
  const response = await fetch('http://localhost:8080/auth/login', {
    method: 'POST',
    headers: { 'content-type': 'application/json' },
    body: JSON.stringify(formData)
  });
  const responseData = await response.json();

  return responseData as ServerDTO<AuthData>;
}
