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

import { createTransferMovements } from '@/services/movements-service';
import {
  transferMovementsFormSchema,
  TransferMovementsFormType
} from '@/types/movements-schema';
import { zodResolver } from '@hookform/resolvers/zod';
import { useRouter } from 'next/navigation';
import { useForm } from 'react-hook-form';
import { toast } from 'react-toastify';
import { StockLocationPopover } from '../../(local-estoque)/components/stock-location-popover';
import { ProductsPopover } from '../../(produtos)/components/products-popover';

export default function TransferMovementsForm() {
  const router = useRouter();
  const hookForm = useForm<TransferMovementsFormType>({
    resolver: zodResolver(transferMovementsFormSchema),
    defaultValues: {
      type: 'TRANSFERENCIA',
      quantity: 0,
      note: '',
      productId: 0,
      fromStockLocationId: 0,
      toStockLocationId: 0
    }
  });

  async function onSubmit(formData: TransferMovementsFormType) {
    const { success, data } = await createTransferMovements(formData);

    if (success) {
      toast.success('Produto transferido com sucesso!');
      hookForm.reset();
      router.refresh();
    }

    if (!success) toast.error('Erro ao transferir produto!');
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
            name='fromStockLocationId'
            render={({ field }) => (
              <FormItem>
                <FormLabel>De Estoque</FormLabel>
                <FormControl>
                  <StockLocationPopover onChange={field.onChange} />
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
                <FormLabel>Para Estoque</FormLabel>
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
