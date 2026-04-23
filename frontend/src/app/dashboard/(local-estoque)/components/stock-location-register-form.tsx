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
import { useCreateStockLocation } from '@/hooks/stock-locations/useCreateStockLocation';
import {
  stockLocationsFormSchema,
  StockLocationsFormType
} from '@/types/stock-location-schema';
import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { toast } from 'react-toastify';

type StockLocationRegisterProps = {
  onCloseDialog?: () => void
}

export default function StockLocationRegisterForm({onCloseDialog}: StockLocationRegisterProps) {
  const hookForm = useForm<StockLocationsFormType>({
    resolver: zodResolver(stockLocationsFormSchema),
    defaultValues: {
      name: ''
    }
  });

  const { mutate, isPending } = useCreateStockLocation()

  const onSubmit = async (data: StockLocationsFormType) => {
    mutate(data, {
      onSuccess: () => {
        toast.success('Local de estoque cadastrado com sucesso!');
        hookForm.reset()
        onCloseDialog?.()
      },
      onError: () => {
        toast.error('Erro ao cadastrar local de estoque!');
      }
    })
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

          <Button 
            type='submit'
            disabled={isPending}
          > {isPending ? 'Cadastrando' : 'Cadastrar'}
          </Button>
        </form>
      </Form>
    </div>
  );
}
