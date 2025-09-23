'use client';

import {
  productFormSchema,
  ProductFormType,
  ProductsData
} from '@/types/product-schema';
import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { Button } from './ui/button';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage
} from './ui/form';
import { Input } from './ui/input';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue
} from './ui/select';

export default function ProductEditForm({
  defaultValues
}: {
  defaultValues: ProductsData;
}) {
  const hookForm = useForm<ProductFormType>({
    resolver: zodResolver(productFormSchema),
    defaultValues
  });
  const id = defaultValues.id;
  const onSubmit = async (data: ProductFormType) => {
    console.log({ ...data, id });

    // if (result.success) toast.success('Produto cadastrado com sucesso!');

    // if (!result.success) toast.error('Erro ao cadastrar produto!');
  };

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
                      <SelectItem value='UND'>UND</SelectItem>
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
            name='stockLocationId'
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

          <Button type='submit'>Salvar Alterações</Button>
        </form>
      </Form>
    </div>
  );
}
