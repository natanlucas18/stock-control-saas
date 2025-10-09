'use client';
import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { z } from 'zod';

const schema = z.object({
  name: z.string().min(6, 'Nome é obrigatório.'),
  email: z.email('Email inválido').min(1, 'Insira seu email'),
  password: z.string().min(1, 'Insira sua senha'),
  rolesId: z.string().array()
});

type RegisterForm = {
  name: string,
  email: string,
  password: string,
  rolesId: string[],
};

export default function Register() {
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting }
  } = useForm<RegisterForm>({
    resolver: zodResolver(schema)
  });

  const onSubmit = async (data: RegisterForm) => {
    console.log(data);
  };
  return (
    <>
      <div className='flex min-h-full h-screen bg-black flex-col justify-center px-6 py-12 lg:px-8'>
        <div className='sm:mx-auto sm:w-full sm:max-w-sm'>
          <img
            alt='Your Company'
            src='https://tailwindcss.com/plus-assets/img/logos/mark.svg?color=white'
            className='mx-auto h-10 w-auto'
          />
          <h2 className='mt-10 text-center text-2xl/9 font-bold tracking-tight text-white'>
            Criar nova conta!
          </h2>
        </div>

        <div className='mt-10 sm:mx-auto sm:w-full sm:max-w-sm'>
          <form
            onSubmit={handleSubmit(onSubmit)}
            className='space-y-6'
          >
            <div>
              <label
                htmlFor='name'
                className='block text-sm/6 font-medium text-gray-100'
              >
                Nome
              </label>
              <div className='mt-2'>
                <input
                  id='name'
                  type='text'
                  required
                  className='block w-full rounded-md bg-white/5 px-3 py-1.5 text-base text-white outline-1 -outline-offset-1 outline-white/10 placeholder:text-gray-500 focus:outline-2 focus:-outline-offset-2 focus:outline-blue-800 sm:text-sm/6'
                  {...register('name')}
                />
                {errors.name && (
                  <span className='text-red-500 text-sm'>
                    {errors.name.message}
                  </span>
                )}
              </div>
            </div>

            <div>
              <label
                htmlFor='email'
                className='block text-sm/6 font-medium text-gray-100'
              >
                Email
              </label>
              <div className='mt-2'>
                <input
                  id='email'
                  type='email'
                  required
                  autoComplete='email'
                  className='block w-full rounded-md bg-white/5 px-3 py-1.5 text-base text-white outline-1 -outline-offset-1 outline-white/10 placeholder:text-gray-500 focus:outline-2 focus:-outline-offset-2 focus:outline-blue-800 sm:text-sm/6'
                  {...register('email')}
                />
                {errors.email && (
                  <span className='text-red-500 text-sm'>
                    {errors.email.message}
                  </span>
                )}
              </div>
            </div>

            <div>
              <div className='flex items-center justify-between'>
                <label
                  htmlFor='password'
                  className='block text-sm/6 font-medium text-gray-100'
                >
                  Senha
                </label>
              </div>
              <div className='mt-2'>
                <input
                  id='password'
                  type='password'
                  required
                  autoComplete='current-password'
                  className='block w-full rounded-md bg-white/5 px-3 py-1.5 text-base text-white outline-1 -outline-offset-1 outline-white/10 placeholder:text-gray-500 focus:outline-2 focus:-outline-offset-2 focus:outline-blue-800 sm:text-sm/6'
                  {...register('password')}
                />
                {errors.password && (
                  <span className='text-red-500 text-sm'>
                    {errors.password.message}
                  </span>
                )}
              </div>
            </div>

            <div>
              <div className='flex items-center justify-between'>
                <label
                  htmlFor='rolesId'
                  className='block text-sm/6 font-medium text-gray-100'
                >
                  Permissões
                </label>
              </div>
              <div className='mt-2'>
                <input
                  id='rolesId'
                  type='text'
                  required
                  className='block w-full rounded-md bg-white/5 px-3 py-1.5 text-base text-white outline-1 -outline-offset-1 outline-white/10 placeholder:text-gray-500 focus:outline-2 focus:-outline-offset-2 focus:outline-blue-800 sm:text-sm/6'
                  {...register('rolesId')}
                />
                {errors.rolesId && (
                  <span className='text-red-500 text-sm'>
                    {errors.rolesId.message}
                  </span>
                )}
              </div>
            </div>

            <div>
              <button
                type='submit'
                className='flex w-full justify-center rounded-md bg-blue-800 px-3 py-1.5 text-sm/6 font-semibold text-white hover:bg-blue-700 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500'
              >
                {isSubmitting ? 'Criando Conta...' : 'Criar'}
              </button>
            </div>
          </form>
        </div>
      </div>
    </>
  );
}
