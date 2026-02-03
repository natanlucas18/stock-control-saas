'use server';

import { authOptions } from '@/app/api/auth/[...nextauth]/route';
import { getServerSession } from 'next-auth';

export async function getToken() {
  const session = await getServerSession(authOptions);
  
  if (session?.user.accessToken) {
    return session.user.accessToken
  }

  return null;
};
