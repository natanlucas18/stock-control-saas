'use server';

import { cookies } from 'next/headers';

export async function getToken() {
  return (await cookies()).get('access_token')?.value || '';
}
