import { login } from '@/app/requests/login-request';
import NextAuth from 'next-auth';
import CredentialsProvider from 'next-auth/providers/credentials';
import { cookies } from 'next/headers';

const handler = NextAuth({
  pages: {
    signIn: '/Login'
  },
  providers: [
    CredentialsProvider({
      name: 'Credentials',
      credentials: {
        username: {},
        password: {}
      },

      async authorize(credentials) {
        if (!credentials) return null;

        try {
          const { data, success } = await login({
            username: credentials.username,
            password: credentials.password
          });

          if (!success) return null;

          await setToken(data.access_token);

          return {
            id: data.user_id,
            name: data.user_name
          };
        } catch (e) {
          return null;
        }
      }
    })
  ]
});

async function setToken(token: string) {
  (await cookies()).set('access_token', token);
}

export { handler as GET, handler as POST };
