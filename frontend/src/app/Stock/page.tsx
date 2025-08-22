"use client"
import { toast } from 'react-toastify'
import Nav from '../Nav/page';
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form'
import { FormDataMovements } from '@/types/types';
import { Button } from "@/components/ui/button"
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"

const schemaMovements = z.object({
  description: z.string().min(1, "Insira o nome do produto!"),
  quantity: z.number().min(1,"Insira a quantidade!")
})



export default function Stock() {
  const { register, handleSubmit, formState: {errors} } = useForm<FormDataMovements>({
    resolver: zodResolver(schemaMovements)
  });

  const handleEntrySubmit = async (data: FormDataMovements) => {
    const type = 'Entry';
    const newData = {...data, type};
    const response = await fetch(`http://localhost:8080/movements`, {
      method: 'POST',
      headers: {
        'content-type': 'application/json',
      },
      body: JSON.stringify(newData)
    });
    if (response.status === 200) {
      toast.success('Operação concluída com sucesso!', {
        position: "top-right",
        autoClose: 1000,
        closeOnClick: true
      });
    } else {
      toast.error('Algo deu errado!', {
        position: "top-right",
        autoClose: 1000,
        closeOnClick: true,
      });
    };
  };

  const handleExitSubmit = async (data: FormDataMovements) => {
    const type = 'Exit';
    const newData = {...data, type};
    const response = await fetch(`http://localhost:8080/movements`, {
      method: 'POST',
      headers: {
        'content-type': 'application/json',
      },
      body: JSON.stringify(newData)
    });
    if (response.status === 200) {
      toast.success('Operação concluída com sucesso!', {
        position: "top-right",
        autoClose: 2000,
      });
    } else {
      toast.error('Algo deu errado!', {
        position: "top-right",
        autoClose: 2000,
        closeOnClick: true,
      });
    };
  };

  const generateLabel = async (data: FormDataMovements) => {
    const date = Date.now();
    const newData = { ...data, date };
    {/*Lógica para conectar a API da zebra*/ }
  }

  return (
    <main className=' w-full flex flex-col gap-2 bg-white'>
      <Nav />
      <section className='w-full'>
        <div className='flex flex-row items-center justify-around mt-8'>
          <Dialog>
            <form onSubmit={handleSubmit(handleEntrySubmit)}>
              <DialogTrigger asChild>
                <Button className='bg-blue-900 w-[9rem] hover:bg-blue-800 hover:text-white rounded-[20px] cursor-pointer text-white mb-2 mt-6 font-bold' variant="outline">Entrada</Button>
              </DialogTrigger>
              <DialogContent className="sm:max-w-[425px]">
                <DialogHeader>
                  <DialogTitle>Entrada de Produto</DialogTitle>
                </DialogHeader>
                <div className="grid gap-4">
                  <div className="grid gap-3">
                    <Label htmlFor="description">Descrição</Label>
                    <Input type='text' id="description" {...register("description")} placeholder='ex.: Caixa papelão 5kg' required/>
                    {errors.description && <p className='text-red-500'>{errors.description.message}</p>}
                  </div>
                  <div className="grid gap-3">
                    <Label htmlFor="quantity">Quantidade</Label>
                    <Input type='number' id="quantity" {...register("quantity")} placeholder='ex.: 10.852' required/>
                    {errors.quantity && <p className='text-red-500'>{errors.quantity.message}</p>}
                  </div>
                </div>
                <DialogFooter>
                  <DialogClose asChild>
                    <Button variant="outline">Cancelar</Button>
                  </DialogClose>
                  <Button type="submit" className='bg-gray-500 hover:bg-gray-600 cursor-pointer'
                    onClick={() => toast.success("Operação realizada com sucesso!", { position: "top-right", autoClose: 1000 })}
                  >Gerar Etiqueta</Button>
                  <Button type="submit" className='bg-blue-900 hover:bg-blue-800 cursor-pointer'>Confirmar</Button>
                </DialogFooter>
              </DialogContent>
            </form>
          </Dialog>
          <Dialog>
            <form onSubmit={handleSubmit(handleExitSubmit)}>
              <DialogTrigger asChild>
                <Button className='bg-blue-900 w-[9rem] rounded-[20px] hover:bg-blue-800 hover:text-white cursor-pointer text-white mb-2 mt-6 font-bold' variant="outline">Saída</Button>
              </DialogTrigger>
              <DialogContent className="sm:max-w-[425px]">
                <DialogHeader>
                  <DialogTitle>Saída de Produto</DialogTitle>
                </DialogHeader>
                <div className="grid gap-4">
                  <div className="grid gap-3">
                    <Label htmlFor="description">Descrição</Label>
                    <Input id="description" {...register("description")} placeholder='ex.: Caixa papelão 5kg' required/>
                  </div>
                  <div className="grid gap-3">
                    <Label htmlFor="quantity-min">Quantidade</Label>
                    <Input id="quantity" {...register("quantity")} placeholder='ex.: 10.852' required/>
                  </div>
                </div>
                <DialogFooter>
                  <DialogClose asChild>
                    <Button variant="outline">Cancelar</Button>
                  </DialogClose>
                  <Button type="submit" className='bg-blue-900 hover:bg-blue-800 cursor-pointer'>Confirmar</Button>
                </DialogFooter>
              </DialogContent>
            </form>
          </Dialog>
        </div>
        <div className='relative overflow-x-auto overflow-y-auto h-[70vh] ml-6 mb-6 mr-6 rounded-sm'>
          <table className='w-full text-sm text-left rtl:text-right text-gray-500 '>
            <thead className='text-xs text-gray-300 uppercase bg-blue-900'>
              <tr>
                <th scope='col' className='px-6 py-3'>Descrição</th>
                <th scope='col' className='px-6 py-3'>nº sessão</th>
                <th scope='col' className='px-6 py-3'>Quantidade</th>
              </tr>
            </thead>
            <tbody>
              <tr className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                <td scope='row' className='px-6 py-4'>cumbuca 500g resipack</td>
                <td scope='row' className='px-6 py-4'>2</td>
                <td scope='row' className='px-6 py-4'>108.000</td>
              </tr>
              <tr className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                <td scope='row' className='px-6 py-4'>cumbuca 500g resipack</td>
                <td scope='row' className='px-6 py-4'>2</td>
                <td scope='row' className='px-6 py-4'>108.000</td>
              </tr>
              <tr className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                <td scope='row' className='px-6 py-4'>cumbuca 500g resipack</td>
                <td scope='row' className='px-6 py-4'>2</td>
                <td scope='row' className='px-6 py-4'>108.000</td>
              </tr>
              <tr className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                <td scope='row' className='px-6 py-4'>cumbuca 500g resipack</td>
                <td scope='row' className='px-6 py-4'>2</td>
                <td scope='row' className='px-6 py-4'>108.000</td>
              </tr>
              <tr className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                <td scope='row' className='px-6 py-4'>cumbuca 500g resipack</td>
                <td scope='row' className='px-6 py-4'>2</td>
                <td scope='row' className='px-6 py-4'>108.000</td>
              </tr>
              <tr className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                <td scope='row' className='px-6 py-4'>cumbuca 500g resipack</td>
                <td scope='row' className='px-6 py-4'>2</td>
                <td scope='row' className='px-6 py-4'>108.000</td>
              </tr>
              <tr className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                <td scope='row' className='px-6 py-4'>cumbuca 500g resipack</td>
                <td scope='row' className='px-6 py-4'>2</td>
                <td scope='row' className='px-6 py-4'>108.000</td>
              </tr>
              <tr className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                <td scope='row' className='px-6 py-4'>cumbuca 500g resipack</td>
                <td scope='row' className='px-6 py-4'>2</td>
                <td scope='row' className='px-6 py-4'>108.000</td>
              </tr>
              <tr className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                <td scope='row' className='px-6 py-4'>cumbuca 500g resipack</td>
                <td scope='row' className='px-6 py-4'>2</td>
                <td scope='row' className='px-6 py-4'>108.000</td>
              </tr>
              <tr className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                <td scope='row' className='px-6 py-4'>cumbuca 500g resipack</td>
                <td scope='row' className='px-6 py-4'>2</td>
                <td scope='row' className='px-6 py-4'>108.000</td>
              </tr>
              <tr className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                <td scope='row' className='px-6 py-4'>cumbuca 500g resipack</td>
                <td scope='row' className='px-6 py-4'>2</td>
                <td scope='row' className='px-6 py-4'>108.000</td>
              </tr>
              <tr className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                <td scope='row' className='px-6 py-4'>cumbuca 500g resipack</td>
                <td scope='row' className='px-6 py-4'>2</td>
                <td scope='row' className='px-6 py-4'>108.000</td>
              </tr>
              <tr className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                <td scope='row' className='px-6 py-4'>cumbuca 500g resipack</td>
                <td scope='row' className='px-6 py-4'>2</td>
                <td scope='row' className='px-6 py-4'>108.000</td>
              </tr>
              <tr className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                <td scope='row' className='px-6 py-4'>cumbuca 500g resipack</td>
                <td scope='row' className='px-6 py-4'>2</td>
                <td scope='row' className='px-6 py-4'>108.000</td>
              </tr>
              <tr className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                <td scope='row' className='px-6 py-4'>cumbuca 500g resipack</td>
                <td scope='row' className='px-6 py-4'>2</td>
                <td scope='row' className='px-6 py-4'>108.000</td>
              </tr>
              <tr className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                <td scope='row' className='px-6 py-4'>cumbuca 500g resipack</td>
                <td scope='row' className='px-6 py-4'>2</td>
                <td scope='row' className='px-6 py-4'>108.000</td>
              </tr>
              <tr className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                <td scope='row' className='px-6 py-4'>cumbuca 500g resipack</td>
                <td scope='row' className='px-6 py-4'>2</td>
                <td scope='row' className='px-6 py-4'>108.000</td>
              </tr>
              <tr className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                <td scope='row' className='px-6 py-4'>cumbuca 500g resipack</td>
                <td scope='row' className='px-6 py-4'>2</td>
                <td scope='row' className='px-6 py-4'>108.000</td>
              </tr>
              <tr className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                <td scope='row' className='px-6 py-4'>cumbuca 500g resipack</td>
                <td scope='row' className='px-6 py-4'>2</td>
                <td scope='row' className='px-6 py-4'>108.000</td>
              </tr>
              <tr className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                <td scope='row' className='px-6 py-4'>cumbuca 500g resipack</td>
                <td scope='row' className='px-6 py-4'>2</td>
                <td scope='row' className='px-6 py-4'>108.000</td>
              </tr>
              <tr className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                <td scope='row' className='px-6 py-4'>cumbuca 500g resipack</td>
                <td scope='row' className='px-6 py-4'>2</td>
                <td scope='row' className='px-6 py-4'>108.000</td>
              </tr>
            </tbody>
          </table>

        </div>
      </section>

    </main>
  )
}