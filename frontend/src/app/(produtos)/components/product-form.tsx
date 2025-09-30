'use client';

import { createProduct } from '@/app/requests/products-request';
import { Button } from '@/components/ui/button';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage
} from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue
} from '@/components/ui/select';
import { productFormSchema, ProductFormType } from '@/types/product-schema';
import { zodResolver } from '@hookform/resolvers/zod';
import { useRouter } from 'next/navigation';
import { useForm } from 'react-hook-form';
import { toast } from 'react-toastify';

export default function ProductForm() {
  const router = useRouter();
  const hookForm = useForm<ProductFormType>({
    resolver: zodResolver(productFormSchema),
    defaultValues: {
      name: '',
      price: 0,
      stockMin: 0,
      stockMax: 0,
      unitMeasure: '',
      stockLocation: {
        id: 0
      }
    }
  });

  async function onSubmit(formData: ProductFormType) {
    const { success } = await createProduct(formData);

    if (success) {
      toast.success('Produto cadastrado com sucesso!');
      hookForm.reset();
      router.refresh();
    }

    if (!success) toast.error('Erro ao cadastrar produto!');
  }

  return (
    <div>
      <Form {...hookForm}>
        <form
          onSubmit={hookForm.handleSubmit(onSubmit)}
          className='space-y-8 border p-5 rounded-md'
        >
          <FormField
            control={hookForm.control}
            name='name'
            render={({ field }) => (
              <FormItem>
                <FormLabel>Nome</FormLabel>
                <FormControl>
                  <Input
                    type='text'
                    {...field}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />

          <FormField
            control={hookForm.control}
            name='price'
            render={({ field }) => (
              <FormItem>
                <FormLabel>Preço</FormLabel>
                <FormControl>
                  <Input
                    type='number'
                    {...field}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />

          <FormField
            control={hookForm.control}
            name='unitMeasure'
            render={({ field }) => (
              <FormItem>
                <FormLabel>Unidade de Medida</FormLabel>
                <FormControl>
                  <Select
                    onValueChange={field.onChange}
                    value={field.value}
                  >
                    <SelectTrigger className='w-[180px]'>
                      <SelectValue placeholder='Unidade de Medida' />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value='UN'>UN</SelectItem>
                      <SelectItem value='KG'>KG</SelectItem>
                    </SelectContent>
                  </Select>
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />

          <FormField
            control={hookForm.control}
            name='stockLocation.id'
            render={({ field }) => (
              <FormItem>
                <FormLabel>ID Local do Estoque</FormLabel>
                <FormControl>
                  <Input
                    type='number'
                    {...field}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />

          <FormField
            control={hookForm.control}
            name='stockMin'
            render={({ field }) => (
              <FormItem>
                <FormLabel>Quantidade Mínima</FormLabel>
                <FormControl>
                  <Input
                    type='number'
                    {...field}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />

          <FormField
            control={hookForm.control}
            name='stockMax'
            render={({ field }) => (
              <FormItem>
                <FormLabel>Quantidade Máximo</FormLabel>
                <FormControl>
                  <Input
                    type='number'
                    {...field}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />

          <Button type='submit'>Salvar Produto</Button>
        </form>
      </Form>
    </div>
  );
}
