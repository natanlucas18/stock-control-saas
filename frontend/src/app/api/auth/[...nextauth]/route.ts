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
        email: {}, 
        password: {},
      },

      async authorize(credentials) {
        if(!credentials) return null;
        try {
          const response = await fetch("http://localhost:8080/auth/login", {
            method: 'POST',
            headers: {"content-type": "application/json"},
            body: JSON.stringify({
              email: credentials.email,
              password: credentials.password,
            }),
          });
          console.log("route", credentials.email, credentials.password)
          if(response.status !== 200) return null;
          const authData:AuthData = await response.json();
          if(!authData.token) return null;
          (await cookies()).set("token", authData.token);
          (await cookies()).set("id", authData.id);
          (await cookies()).set("name", authData.name);
          return {
            id: authData.id,
            name: authData.name,
            email: authData.email,
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