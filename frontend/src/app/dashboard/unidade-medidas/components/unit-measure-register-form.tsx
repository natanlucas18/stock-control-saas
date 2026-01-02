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
import { createUnitMeasure } from '@/services/unit-measure-service';
import { unitMeasureFormSchema, UnitMeasureFormType } from '@/types/unit-measure-schema';
import { zodResolver } from '@hookform/resolvers/zod';
import { useRouter } from 'next/navigation';
import { useForm } from 'react-hook-form';
import { toast } from 'react-toastify';

export default function UnitMeasureRegisterForm() {
  const router = useRouter();
  const hookForm = useForm<UnitMeasureFormType>({
    resolver: zodResolver(unitMeasureFormSchema),
    defaultValues: {
      name: '',
      acronym: '',
    }
  });

  async function onSubmit(formData: UnitMeasureFormType) {
    const { success } = await createUnitMeasure(formData);

    if (success) {
      toast.success('Unidade de medida cadastrada com sucesso!');
      hookForm.reset();
      router.refresh();
    }

    if (!success) toast.error('Erro ao cadastrar!');
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
            name='acronym'
            render={({ field }) => (
              <FormItem>
                <FormLabel>Sigla</FormLabel>
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


          <Button type='submit'>Salvar</Button>
        </form>
      </Form>
    </div>
  );
}
