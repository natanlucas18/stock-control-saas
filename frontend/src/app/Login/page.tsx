"use client";
import React from 'react';
import { useForm } from 'react-hook-form';
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import { signIn } from 'next-auth/react';
import { FormLogin } from '@/types/types';


const schema = z.object({
  username: z.email('Email inválido').min(1, 'Insira seu email'),
  password: z.string().min(1, 'Insira sua senha'),
});

export default function Login() {
  const { register, handleSubmit, formState: { errors } } = useForm<FormLogin>({
    resolver: zodResolver(schema)
  });


  const onSubmit = async (data: FormLogin) => {
    signIn("credentials", {
      ...data,
      callbackUrl: "/Home",
    });
  };
  return (
    <>
      <div className="flex min-h-full h-screen bg-black flex-col justify-center px-6 py-12 lg:px-8">
        <div className="sm:mx-auto sm:w-full sm:max-w-sm">
          <img
            alt="Your Company"
            src="https://tailwindcss.com/plus-assets/img/logos/mark.svg?color=white"
            className="mx-auto h-10 w-auto"
          />
          <h2 className="mt-10 text-center text-2xl/9 font-bold tracking-tight text-white">Faça Login com Sua Conta!</h2>
        </div>

        <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
            <div>
              <label htmlFor="email" className="block text-sm/6 font-medium text-gray-100">
                Email
              </label>
              <div className="mt-2">
                <input
                  id="email"
                  type="email"
                  required
                  autoComplete="email"
                  className="block w-full rounded-md bg-white/5 px-3 py-1.5 text-base text-white outline-1 -outline-offset-1 outline-white/10 placeholder:text-gray-500 focus:outline-2 focus:-outline-offset-2 focus:outline-blue-800 sm:text-sm/6"
                  {...register("username")}
                />
                {errors.username && (
                  <span className='text-red-500 text-sm'>{errors.username.message}</span>
                )}
              </div>
            </div>

            <div>
              <div className="flex items-center justify-between">
                <label htmlFor="password" className="block text-sm/6 font-medium text-gray-100">
                  Senha
                </label>
              </div>
              <div className="mt-2">
                <input
                  id="password"
                  type="password"
                  required
                  autoComplete="current-password"
                  className="block w-full rounded-md bg-white/5 px-3 py-1.5 text-base text-white outline-1 -outline-offset-1 outline-white/10 placeholder:text-gray-500 focus:outline-2 focus:-outline-offset-2 focus:outline-blue-800 sm:text-sm/6"
                  {...register("password")}
                />
                {errors.password && (
                  <span className='text-red-500 text-sm'>{errors.password.message}</span>
                )}
              </div>
            </div>

            <div>
              <button
                type="submit"
                className="flex w-full justify-center rounded-md bg-blue-800 px-3 py-1.5 text-sm/6 font-semibold text-white hover:bg-blue-700 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500"
              >
                Entrar
              </button>
            </div>
          </form>
        </div>
      </div>
    </>
  );
}