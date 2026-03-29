import { betterAuth } from "better-auth";
import { drizzleAdapter } from "better-auth/adapters/drizzle";
import { jwt } from "better-auth/plugins";
import * as dbModule from "./db"; 
import { account, session, user } from "./db/schema";
import { getApiUrl } from "./api-url";

export type User = {
    email: string;
    role: string[];
}
export const localhost = getApiUrl();
const db = dbModule.db;

export const auth = betterAuth({
    database: drizzleAdapter(db, {
        provider: "pg",
        schema: {
            user: user,
            session: session,
            account: account,
        },
    }),
    
    user: {
        additionalFields: {
            role: {
                type: "string[]",
            },
        },
    },

   plugins: [
        jwt({
            jwt: {
                expirationTime: "15m",
                issuer: process.env.BETTER_AUTH_ISSUER,
                audience: process.env.BETTER_AUTH_AUDIENCE,
            },
            payload: (user: User) => ({
                roles: user.role,
                email: user.email,
            }),
        }),
    ],

    session: {
        expiresIn: 60 * 60 * 24 * 7,
        updateAge: 60 * 60 * 24,  
    },

    providers: {
        credentials: {
                authorize: async (credentials: Record<string, string>) => {
                    const res = await fetch(`${localhost}/api/auth/login`, {
                        method: "POST",
                        body: JSON.stringify({
                            username: credentials.email,
                            password: credentials.password,
                        }),
                        headers: { "Content-Type": "application/json" },
                    });
                    const data = await res.json() as ServerAuthResponse;
                    if (!res.ok) return null;
                    
                    return {
                        id: data.data.userId,
                        email: data.data.userEmail,
                        name: data.data.userName,
                        role: data.data.userRoles
                    };
                },
        },
    },
});