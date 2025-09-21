"use client"
import { useState, useEffect } from 'react';
import { toast } from 'react-toastify';
import {MdModeEdit} from 'react-icons/md'
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form'
import { FormStockLocation, StockLocationData } from '@/types/types';
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

const schemaEditLocation = z.object({
  name: z.string().min(1, "Insira uma descrição"),
})
export function EditStockLocation() {
  const { register, handleSubmit, formState: { errors, isSubmitting } } = useForm<FormStockLocation>({
    resolver: zodResolver(schemaEditLocation),
    defaultValues: {
    }
  });
  return (
    <Dialog>
      <form onSubmit={handleSubmit(onsubmit)}>
        <DialogTrigger asChild>
          <Button className='bg-blue-600 hover:bg-blue-800 cursor-pointer' variant="outline"><MdModeEdit className='text-white'/></Button>
        </DialogTrigger>
        <DialogContent className="sm:max-w-[425px]">
          <DialogHeader>
            <DialogTitle>Editar</DialogTitle>
          </DialogHeader>
          <div className="grid gap-4">
            <div className="grid gap-3">
              <Label htmlFor="name">Descrição</Label>
              <Input id="name" {...register("name")} required />
            </div>
          </div>
          <DialogFooter>
            <DialogClose asChild>
              <Button variant="outline">Cancelar</Button>
            </DialogClose>
            <Button type="submit" className='bg-blue-900 hover:bg-blue-800 cursor-pointer'>{isSubmitting ? 'Alterando...' : 'Confirmar'}</Button>
          </DialogFooter>
        </DialogContent>
      </form>
    </Dialog>

  )
}