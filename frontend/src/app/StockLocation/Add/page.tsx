"use client"
import Nav from '../../Nav/page';
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { useForm } from 'react-hook-form';
import { toast } from 'react-toastify';
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';
import { FormStockLocation } from '@/types/types';
import { getCookie } from 'cookies-next/client';
import { getServerSession } from 'next-auth';
import { redirect } from 'next/navigation';

const schema = z.object({
  name: z.string().min(1, 'Insira um nome para a localização no estoque!'),
})

export default function CreateStockLocation() {
  const { register, handleSubmit, formState: { errors, isSubmitting } } = useForm<FormStockLocation>({
    resolver: zodResolver(schema),
  });
  const accessToken = getCookie("accessToken");
  const session = getServerSession();

  if(!session) {
    redirect('/Login');
  }


  // Criando uma nova localização no estoque
  const onSubmit = async (data: FormStockLocation) => {
    console.log(data);
    console.log(accessToken);
    await fetch('http://localhost:8080/api/stock-locations', {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${accessToken}`
      },
      body: JSON.stringify(data),
    })
    .then(() => {
      toast.success("Operação realizada com sucesso!");
    })
    .catch(err => {
     toast.error("Ocorreu um erro!");
     console.log(err);
    })

  }

  return (
    <>
      <Nav />
      <div className="flex h-[91vh] bg-white flex-col justify-center px-6 py-12 lg:px-8">
        <div className="sm:mx-auto sm:w-full sm:max-w-sm">
          <h2 className="mt-2 text-center text-2xl/9 font-bold tracking-tight text-black">Nova localização no estoque</h2>
        </div>
        <div className="mt-8 mb-4 sm:mx-auto sm:w-full sm:max-w-sm">
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
            <div>
              <Label htmlFor="name" className="block text-sm/6 font-medium text-black">
                Nome
              </Label>
              <div className="mt-2">
                <Input
                  id="name"
                  type="text"
                  required
                  className="block w-full rounded-md bg-black/10 px-3 py-1.5 text-base text-black outline-1 -outline-offset-1 outline-white/10 placeholder:text-gray-500 focus:outline-2 focus:-outline-offset-2 focus:outline-black sm:text-sm/6"
                  {...register("name")}
                />
                {errors.name && (
                  <span className='text-red-500 text-sm'>{errors.name.message}</span>
                )}
              </div>
            </div>
            <div>
              <Button
                type="submit"
                className="flex w-full justify-center rounded-md bg-blue-800 px-3 py-1.5 text-sm/6 font-semibold text-white hover:bg-blue-700 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500"
                disabled={isSubmitting}
              >
                {isSubmitting ? 'Criando...' : 'Criar'}
              </Button>
            </div>
          </form>
        </div>
      </div>
    </>

  )
}