import { login } from '@/services/login-service';
import { PathLinks } from '@/types/path-links';
import NextAuth, { NextAuthOptions } from 'next-auth';
import CredentialsProvider from 'next-auth/providers/credentials';
import { cookies } from 'next/headers';

export const authOptions: NextAuthOptions = {
  pages: {
    signIn: PathLinks.SIGN_IN
  },
  session: {
    strategy: 'jwt' as const,
    maxAge: 30 * 24 * 60 * 60 // 30 dias
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

          (await cookies()).set('userRoles', data.userRoles.toString(), {
            httpOnly: true,
            secure: true,
            sameSite: 'strict',
            path: '/dashboard'
          })

          return {
            id: data.userId,
            name: data.userName,
            accessToken: data.accessToken,
            userRoles: data.userRoles,
            expiresAt: data.expiresAt
          };
        } catch {
          return null;
        }
      }
    })
  ],
    callbacks: {
    async jwt({ user, token }) {
      if(user) {
      token.sub = user.id;
      token.name = user.name;
      token.email = user.email;
      token.accessToken = user.accessToken;
      token.userRoles = user.userRoles;
      token.expiresAt = new Date(user.expiresAt).getTime()
      };
      return token;
    },
    async session({ session, token }) {
      if (session.user) {
      session.user.id = token.sub as string;
      session.user.name = token.name;
      session.user.email = token.email;
      session.user.accessToken = token.accessToken as string;
      session.user.userRoles = token.userRoles;
      session.expires = new Date(token.expiresAt).toISOString()
      }
      return session;
    }
  }


};


const handler = NextAuth(authOptions)
export { handler as GET, handler as POST };
