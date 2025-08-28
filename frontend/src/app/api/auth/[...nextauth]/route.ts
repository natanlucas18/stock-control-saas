import { AuthData } from '@/types/types';
import NextAuth from 'next-auth';
import  CredentialsProvider  from 'next-auth/providers/credentials';
import { cookies } from 'next/headers';

const handler = NextAuth({
  pages:{
    signIn: "/Login"
  },
  providers: [
    CredentialsProvider({
      name: "Credentials",
      credentials:{
        username: {}, 
        password: {},
      },

      async authorize(credentials) {
        if(!credentials) return null;
        try {
          const response = await fetch("http://localhost:8080/auth/login", {
            method: 'POST',
            headers: {"content-type": "application/json"},
            body: JSON.stringify({
              username: credentials.username,
              password: credentials.password,
            }),
          });
          console.log("route", credentials.username, credentials.password)
          if(response.status !== 200) return null;
          const authData:AuthData = await response.json();
          if(!authData.accessToken) return null;
          (await cookies()).set("accessToken", authData.accessToken);
          return {
            accessToken: authData.accessToken,
          }
        } catch(e) {
          console.log(e);
          return;
        };
      }
    })
  ]
})

export {handler as GET, handler as POST};