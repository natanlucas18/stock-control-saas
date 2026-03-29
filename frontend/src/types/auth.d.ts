import { auth } from "@/lib/auth";

type Session = typeof auth.$Infer.Session;
type User = typeof auth.$Infer.User;

export type { Session, User };