import { login } from '@/app/requests/login-request';
import { PathLinks } from '@/types/path-links';
import NextAuth from 'next-auth';
import CredentialsProvider from 'next-auth/providers/credentials';
import { cookies } from 'next/headers';

const handler = NextAuth({
  pages: {
    signIn: PathLinks.LOGIN
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

          await setCookie('accessToken', data.accessToken);
          await setCookie('userRoles', JSON.stringify(data.userRoles));

          return {
            id: data.userId,
            name: data.userName
          };
        } catch {
          return null;
        }
      }
    })
  ]
});

async function setCookie(key: string, value: string) {
  (await cookies()).set(key, value);
}

export { handler as GET, handler as POST };
