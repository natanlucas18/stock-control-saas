import z from 'zod';

export type LoginForm = {
  username: string;
  password: string;
};

export const loginFormSchema = z.object({
  username: z.string().min(1, 'Insira um nome de usuário!'),
  password: z.string().min(1, 'Insira uma senha!')
});

export type LoginFormType = z.infer<typeof loginFormSchema>;
