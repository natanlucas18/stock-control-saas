'use client';

import { ProductsPopover } from '@/app/(produtos)/components/products-popover';
import { createMovements } from '@/app/services/movements-service';
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
import {
  movementsFormSchema,
  MovementsFormType
} from '@/types/movements-schema';
import { zodResolver } from '@hookform/resolvers/zod';
import { useRouter } from 'next/navigation';
import { useForm } from 'react-hook-form';
import { toast } from 'react-toastify';

export default function MovementsForm() {
  const router = useRouter();
  const hookForm = useForm<MovementsFormType>({
    resolver: zodResolver(movementsFormSchema),
    defaultValues: {
      type: 'ENTRADA',
      quantity: 0,
      note: '',
      productId: 0,
      stockLocationId: 0
    }
  });

  async function onSubmit(formData: MovementsFormType) {
    const { success, data } = await createMovements(formData);

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
            name='type'
            render={({ field }) => (
              <FormItem>
                <FormLabel>Tipo</FormLabel>
                <FormControl>
                  <Select
                    onValueChange={field.onChange}
                    value={field.value}
                  >
                    <SelectTrigger className='w-[180px]'>
                      <SelectValue placeholder='Unidade de Medida' />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value='ENTRADA'>ENTRADA</SelectItem>
                      <SelectItem value='SAIDA'>SAIDA</SelectItem>
                    </SelectContent>
                  </Select>
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />

          <FormField
            control={hookForm.control}
            name='productId'
            render={({ field }) => (
              <FormItem>
                <FormLabel>Produto</FormLabel>
                <FormControl>
                  <ProductsPopover onChange={field.onChange} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />

          <FormField
            control={hookForm.control}
            name='quantity'
            render={({ field }) => (
              <FormItem>
                <FormLabel>Quantidade</FormLabel>
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
            name='note'
            render={({ field }) => (
              <FormItem>
                <FormLabel>Observações</FormLabel>
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
            name='stockLocationId'
            render={({ field }) => (
              <FormItem>
                <FormLabel>ID do Estoque</FormLabel>
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

          <Button type='submit'>Enviar</Button>
        </form>
      </Form>
    </div>
  );
}
