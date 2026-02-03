import NextAuth from "next-auth"

declare module "next-auth" {
  interface User {
    accessToken: string
    userRoles: []
    expiresAt: string
  }

  interface Session {
    user: {
      id: string
      accessToken: string
      userRoles: []
      name?: string | null
      email?: string | null
      image?: string | null
    }
  }
}

declare module "next-auth/jwt" {
  interface JWT {
    accessToken: string
    userRoles: []
    expiresAt: number
  }
}
