'use server';

import { cookies } from 'next/headers';

export async function getCookie(key: string) {
  return (await cookies()).get(key)?.value || '';
}
