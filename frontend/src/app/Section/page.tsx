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
import { FormSection, SectionsData } from '@/types/types';



const schema = z.object({
  description: z.string().min(1, 'Insira uma descrição para a sessão!'),
})



export default function Section() {
  const [Sections, setSections] = useState<SectionsData>([]);
  const { register, handleSubmit, formState: { errors } } = useForm<FormSection>({
    resolver: zodResolver(schema),
  });

  // Buscando todos as sessões
  useEffect(() => {
    const getSections = async () => {
      const response = await fetch(`http://localhost:8080/api/stockLocations`);
      const data = await response.json();
      setSections(data);
    }
    getSections();
  }, []);

  // Criando uma nova sessão
  const onSubmit = async (data: FormSection) => {
    try {
      const response = await fetch(`http://localhost:8080/api/locations`, {
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

  const deleteSection = async (id: SectionsData) => {
    const response = await fetch(`http://localhost:8080/api/locations/${id}`);
    if (response.status <= 205) return toast.success("Sessão deletada com sucesso!");
    return toast.error("Erro ao deletar sessão!");
  };

  return (
    <>
      <Nav />
      <main>
        <section className=' w-full'>
          <Dialog>
            <form onSubmit={handleSubmit(onSubmit)}>
              <DialogTrigger asChild>
                <Button className='bg-blue-900 hover:bg-blue-800 rounded-lg hover:text-white cursor-pointer text-white mb-2 ml-6 mt-6 font-bold' variant="outline">Nova Sessão</Button>
              </DialogTrigger>
              <DialogContent className="sm:max-w-[425px]">
                <DialogHeader>
                  <DialogTitle>Nova Sessão</DialogTitle>
                </DialogHeader>
                <div className="grid gap-4">
                  <div className="grid gap-3">
                    <Label htmlFor="description">Descrição</Label>
                    <Input id="description" {...register("description")} placeholder='ex.: Cumbucas' required />
                    {errors.description && <p className='text-red-500'>{errors.description.message}</p>}
                  </div>
                </div>
                <DialogFooter>
                  <DialogClose asChild>
                    <Button variant="outline">Cancelar</Button>
                  </DialogClose>
                  <Button type="submit" className='bg-blue-900 hover:bg-blue-800 cursor-pointer'>Criar sessão</Button>
                </DialogFooter>
              </DialogContent>
            </form>
          </Dialog>

          <div className='relative overflow-x-auto overflow-y-auto h-[70vh] ml-6 mb-6 mr-6 rounded-sm'>
            <table className='w-full text-sm text-left rtl:text-right text-gray-500'>
              <thead className='text-xs text-gray-300 uppercase bg-blue-900'>
                <tr>
                  <th scope='col' className='px-6 py-3'>Nº da sessão</th>
                  <th scope='col' className='px-6 py-3'>Descrição</th>
                </tr>
              </thead>
              <tbody>
                {Sections?.map(section => (
                  <tr key={section.id} className='bg-gray-300 border-b uppercase text-black border-gray-200'>
                    <td scope='row' className='px-6 py-4'>{section.id}</td>
                    <td scope='row' className='px-6 py-4'>{section.description}</td>
                    <td scope='row' className='px-6 py-4 flex flex-row gap-2'>
                      <span className='text-[1.5rem] cursor-pointer text-blue-400 hover:text-blue-600'>
                        <CiEdit />
                      </span>
                      <span className='text-[1.5rem] cursor-pointer text-red-500 hover:text-red-700' onClick={() => deleteSection(section.id)}>
                        <MdDelete />
                      </span>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>

          </div>
        </section>
      </main>
    </>

  )
}