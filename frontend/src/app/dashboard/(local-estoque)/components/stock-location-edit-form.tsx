'use client';

import { updateStockLocation } from '@/app/services/stock-location-request';
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
  StockLocationsData,
  stockLocationsFormSchema,
  StockLocationsFormType
} from '@/types/stock-location-schema';
import { zodResolver } from '@hookform/resolvers/zod';
import { useRouter } from 'next/navigation';
import { useForm } from 'react-hook-form';
import { toast } from 'react-toastify';

export default function StockLocationEditForm({
  defaultValues
}: {
  defaultValues: StockLocationsData;
}) {
  const router = useRouter();
  const hookForm = useForm<StockLocationsFormType>({
    resolver: zodResolver(stockLocationsFormSchema),
    defaultValues
  });
  const id = defaultValues.id;
  const onSubmit = async (data: StockLocationsFormType) => {
    const { success } = await updateStockLocation(id, data);

    if (success) {
      toast.success('Local de estoque editado com sucesso!');
      hookForm.reset();
      router.refresh();
    }
    if (!success) {
      toast.error('Erro ao editar local de estoque!');
    }
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

          <Button type='submit'>Salvar Alteração</Button>
        </form>
      </Form>
    </div>
  );
}
