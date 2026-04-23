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
import { useCreateUnitMeasure } from '@/hooks/unit-measures/useCreateUnitMeasure';
import { createUnitMeasure } from '@/services/unit-measure-service';
import { unitMeasureFormSchema, UnitMeasureFormType } from '@/types/unit-measure-schema';
import { zodResolver } from '@hookform/resolvers/zod';
import { useRouter } from 'next/navigation';
import { useForm } from 'react-hook-form';
import { toast } from 'react-toastify';

type UnitMeasureRegisterFormProps = {
  onClosedDialog?: () => void;
}

export default function UnitMeasureRegisterForm(
  { onClosedDialog }: UnitMeasureRegisterFormProps
) {
  const router = useRouter();
  const hookForm = useForm<UnitMeasureFormType>({
    resolver: zodResolver(unitMeasureFormSchema),
    defaultValues: {
      name: '',
      acronym: '',
    }
  });

  const { mutate, isPending } = useCreateUnitMeasure();

  async function onSubmit(formData: UnitMeasureFormType) {
    mutate(formData, {
      onSuccess: () => {
        toast.success('Unidade de medida cadastrada com sucesso!');
        hookForm.reset();
        router.refresh();
        onClosedDialog?.()
      },
      onError: () => {
        toast.error('Erro ao cadastrar!');
      }
    })
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

          <Button 
            type='submit'
            disabled={isPending}
          > {isPending ? 'Salvando' : 'Salvar'}
          </Button>
        </form>
      </Form>
    </div>
  );
}
