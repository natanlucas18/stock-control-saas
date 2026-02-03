'use client';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { LoginForm } from '@/types/login-schema';
import { PathLinks } from '@/types/path-links';
import { zodResolver } from '@hookform/resolvers/zod';
import { signIn } from 'next-auth/react';
import { useRouter } from 'next/navigation';
import { useForm } from 'react-hook-form';
import { toast } from 'react-toastify';
import { z } from 'zod';

const schema = z.object({
  username: z.email('Email inválido').min(1, 'Insira seu email'),
  password: z.string().min(1, 'Insira sua senha')
});

export default function SignInPage() {
  const router = useRouter();
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting }
  } = useForm<LoginForm>({
    resolver: zodResolver(schema)
  });

  const onSubmit = async (data: LoginForm) => {
    const id = toast.loading('Entrando...')

    const result = await signIn('credentials', {
      ...data,
      redirect: false
    });

    if (result?.error) {
      toast.error('E-mail ou senha inválidos')
      toast.dismiss(id)
      return
    }

    toast.success('Login realizado com sucesso!')
    toast.dismiss(id)
    router.push(PathLinks.DASHBOARD)
  };

  return (
    <main className='flex items-center justify-center min-h-screen px-4'>
      <Card className='w-full max-w-md shadow-md'>
        <CardHeader className='space-y-1 text-center'>
          <img
            alt='Your Company'
            src='https://tailwindcss.com/plus-assets/img/logos/mark.svg?color=white'
            className='mx-auto h-10 w-auto'
          />
          <h1 className='text-2xl font-bold'>Login</h1>
        </CardHeader>

        <CardContent>
          <form
            onSubmit={handleSubmit(onSubmit)}
            className='space-y-4'
          >
            <div className='space-y-1'>
              <Input
                placeholder='E-mail'
                type='email'
                {...register('username')}
              />
              {errors.username && (
                <p className='text-sm text-red-500'>
                  {errors.username.message}
                </p>
              )}
            </div>

            <div className='space-y-1'>
              <Input
                placeholder='Senha'
                type='password'
                {...register('password')}
              />
              {errors.password && (
                <p className='text-sm text-red-500'>
                  {errors.password.message}
                </p>
              )}
            </div>

            <Button
              type='submit'
              disabled={isSubmitting}
              className='w-full'
            >
              {isSubmitting ? 'Entrando...' : 'Entrar'}
            </Button>
          </form>
        </CardContent>
      </Card>
    </main>

  );
}
