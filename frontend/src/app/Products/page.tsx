"use client"
import Nav from '../Nav/page';
import { CiEdit } from 'react-icons/ci'
import { MdDelete } from 'react-icons/md';
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
import { useForm } from 'react-hook-form';
import { toast } from 'react-toastify';
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';
import { useState, useEffect } from 'react';
import { FormProducts, ProductsData } from '@/types/types';



const schema = z.object({
  description: z.string().min(1, 'Insira uma descrição para o produto!'),
  idSession: z.number().min(1, 'Insira a sessão na qual o produto pertence!'),
  quantity_min: z.number().min(1, 'Insira uma quantidade minima para o produto!'),
  quantity_max: z.number().min(1, 'Insira uma quantidade máxima para o produto!'),
})
export default function Products() {
  const [products, setProducts] = useState<ProductsData>([]);
  const { register, handleSubmit, formState: { errors } } = useForm<FormProducts>({
    resolver: zodResolver(schema),
  });

  // Buscando todos os Produtos
  useEffect(() => {
    const getProducts = async () => {
      const response = await fetch(`http://localhost:8080/api/products`);
      const data = await response.json();
      setProducts(data);
    }
    getProducts();
  }, []);

  // Fazendo um POST de um produto
  const onSubmit = async (data: FormProducts) => {
    try {
      const response = await fetch(`http://localhost:8080/api/products`, {
        method: 'POST',
        headers: {
          'content-type': 'application/json',
        },
        body: JSON.stringify(data),
      })
      if (response.status === 200) {
        return toast.success("Operação realizada com sucesso!");
      } else {
        toast.error("Ocorreu um erro!");
      };
    } catch (error) {
      return console.error(error);
    };
  };

  const deleteProduct = async (id: ProductsData) => {
    const response = await fetch(`http://localhost:8080/api/products/${id}`);
    if (response.status <= 205) return toast.success("Produto deletado com sucesso!");
    return toast.error("Erro ao deletar produto!");
  };

  return (
    <>
      <Nav />
      <main className=' w-full flex flex-col gap-2 bg-white'>
        <section className=' w-full'>
          <Dialog>
            <form onSubmit={handleSubmit(onSubmit)}>
              <DialogTrigger asChild>
                <Button className='bg-blue-900 hover:bg-blue-800 rounded-lg hover:text-white cursor-pointer text-white mb-2 ml-6 mt-6 font-bold' variant="outline">Novo produto</Button>
              </DialogTrigger>
              <DialogContent className="sm:max-w-[425px]">
                <DialogHeader>
                  <DialogTitle>Novo Produto</DialogTitle>
                </DialogHeader>
                <div className="grid gap-4">
                  <div className="grid gap-3">
                    <Label htmlFor="description">Descrição</Label>
                    <Input id="description" {...register("description")} placeholder='ex.: Caixa papelão 5kg' required />
                    {errors.description && <p className='text-red-500'>{errors.description.message}</p>}
                  </div>
                  <div className="grid gap-3">
                    <Label htmlFor="idSession">Nº da sessão</Label>
                    <Input type='number' id="idSession" {...register("idSession")} placeholder='ex.: 2' required />
                    {errors.idSession && <p className='text-red-500'>{errors.idSession.message}</p>}
                  </div>
                  <div className="grid gap-3">
                    <Label htmlFor="quantity-min">Quantidade mínima</Label>
                    <Input id="quantity-min" {...register("quantity_min")} placeholder='ex.: 10.852' required />
                    {errors.quantity_min && <p className='text-red-500'>{errors.quantity_min.message}</p>}
                  </div>
                  <div className="grid gap-3">
                    <Label htmlFor="quantity-max">Quantidade máxima</Label>
                    <Input id="quantity-max" {...register("quantity_max")} placeholder='ex.: 24.991' required />
                    {errors.quantity_max && <p className='text-red-500'>{errors.quantity_max.message}</p>}
                  </div>
                </div>
                <DialogFooter>
                  <DialogClose asChild>
                    <Button variant="outline">Cancelar</Button>
                  </DialogClose>
                  <Button type="submit" className='bg-blue-900 hover:bg-blue-800 cursor-pointer'>Criar produto</Button>
                </DialogFooter>
              </DialogContent>
            </form>
          </Dialog>

          <div className='relative overflow-x-auto overflow-y-auto h-[70vh] ml-6 mb-6 mr-6 rounded-sm'>
            <table className='w-full text-sm text-left rtl:text-right text-gray-500'>
              <thead className='text-xs text-gray-300 uppercase bg-blue-900'>
                <tr>
                  <th scope='col' className='px-6 py-3'>Código</th>
                  <th scope='col' className='px-6 py-3'>Descrição</th>
                  <th scope='col' className='px-6 py-3'>nº sessão</th>
                  <th scope='col' className='px-6 py-3'>Opções</th>
                </tr>
              </thead>
              <tbody>
                {products? (
                products?.map(product => (
                  <tr key={product.id} className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                    <td scope='row' className='px-6 py-4'>{product.code}</td>
                    <td scope='row' className='px-6 py-4'>{product.description}</td>
                    <td scope='row' className='px-6 py-4'>{product.idSession}</td>
                    <td scope='row' className='px-6 py-4 flex flex-row gap-2'>
                      <span className='text-[1.5rem] cursor-pointer text-blue-400 hover:text-blue-600'>
                        <CiEdit />
                      </span>
                      <span className='text-[1.5rem] cursor-pointer text-red-500 hover:text-red-700' onClick={() => deleteProduct(product.id)}>
                        <MdDelete />
                      </span>
                    </td>
                  </tr>
                ))): (
                  <tr className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                    <td scope='row' className='px-6 py-4'>Nenhum produto cadastrado</td>
                  </tr>
                )}
              </tbody>
            </table>

          </div>
        </section>

      </main>
    </>
  )
}

