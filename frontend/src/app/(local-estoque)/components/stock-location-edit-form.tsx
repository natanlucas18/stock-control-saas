'use client';
import {
  StockLocationsData,
  stockLocationsFormSchema,
  StockLocationsFormType
} from '@/types/stock-location-schema';
import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { Button } from '../../../components/ui/button';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage
} from '../../../components/ui/form';
import { Input } from '../../../components/ui/input';

export default function StockLocationEditForm({
  defaultValues
}: {
  defaultValues: StockLocationsData;
}) {
  const hookForm = useForm<StockLocationsFormType>({
    resolver: zodResolver(stockLocationsFormSchema),
    defaultValues
  });
  const id = defaultValues.id;
  const onSubmit = async (data: StockLocationsFormType) => {
    console.log({ ...data, id });

    // if (result.success) toast.success('Alteração feita com sucesso!');

    // if (!result.success) toast.error('Erro ao salvar alteração!');
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
