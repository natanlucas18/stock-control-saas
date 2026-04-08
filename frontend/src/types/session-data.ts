export type SessionData = {
    user: {
      id: number;
      name: string;
      role: string[];
    };
    expiresAt: number
};