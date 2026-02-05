'use client';

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

import { createReturnMovements } from '@/services/movements-service';
import {
  returnMovementsFormSchema,
  ReturnMovementsFormType
} from '@/types/movements-schema';
import { zodResolver } from '@hookform/resolvers/zod';
import { useRouter } from 'next/navigation';
import { useForm } from 'react-hook-form';
import { toast } from 'react-toastify';
import { StockLocationPopover } from '../../(local-estoque)/components/stock-location-popover';
import { ProductsPopover } from '../../(produtos)/components/products-popover';

export default function ReturnMovementsForm() {
  const router = useRouter();
  const hookForm = useForm<ReturnMovementsFormType>({
    resolver: zodResolver(returnMovementsFormSchema),
    defaultValues: {
      type: 'DEVOLUCAO',
      quantity: 0,
      note: '',
      productId: 0,
      toStockLocationId: 0
    }
  });

  async function onSubmit(formData: ReturnMovementsFormType) {
    const { success, data } = await createReturnMovements(formData);

    if (success) {
      toast.success('Produto devolvido com sucesso!');
      hookForm.reset();
      router.refresh();
    }

    if (!success) toast.error('Erro ao devolver produto!');
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
            name='toStockLocationId'
            render={({ field }) => (
              <FormItem>
                <FormLabel>ID do Estoque</FormLabel>
                <FormControl>
                  <StockLocationPopover onChange={field.onChange} />
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
