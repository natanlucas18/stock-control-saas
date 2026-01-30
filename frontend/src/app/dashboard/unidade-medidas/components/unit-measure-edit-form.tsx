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
import { editUnitMeasure } from '@/services/unit-measure-service';
import { UnitMeasureData, unitMeasureFormSchema, UnitMeasureFormType } from '@/types/unit-measure-schema';
import { zodResolver } from '@hookform/resolvers/zod';
import { useRouter } from 'next/navigation';
import { useForm } from 'react-hook-form';
import { toast } from 'react-toastify';

export default function UnitMeasureEditForm({
  defaultValues,
  onOpenChange
}: {
  defaultValues: UnitMeasureData
  onOpenChange?: (open: boolean) => void;
}) {
  const router = useRouter();
  const hookForm = useForm<UnitMeasureFormType>({
    resolver: zodResolver(unitMeasureFormSchema),
    defaultValues
  });
  const UnitMeasureId = defaultValues.id;

  async function onSubmit(formData: UnitMeasureFormType) {
    const { success } = await editUnitMeasure(formData, UnitMeasureId);

    if (success) {
      toast.success('Unidade de medida editada com sucesso!');
      hookForm.reset();
      router.refresh();
      if (onOpenChange) onOpenChange(false);
    }

    if (!success) toast.error('Erro ao editar unidade de medida!');
  }

  return (
    <Form {...hookForm}>
      <form
        onSubmit={hookForm.handleSubmit(onSubmit)}
        className='space-y-8 border p-5 rounded-md'
        id='unit-measure-edit-form'
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
          form='unit-measure-edit-form'
        >
          Salvar Alterações
        </Button>
      </form>
    </Form>
  );
}
