'use client';

import CustomTooltip from '@/components/custom-tooltip';
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
import { objectToFormData } from '@/lib/utils';
import { filterFormSchema, FilterFormType } from '@/types/filter-schema';
import { zodResolver } from '@hookform/resolvers/zod';
import { EraserIcon, SlidersHorizontalIcon } from 'lucide-react';
import { useRouter } from 'next/navigation';
import { startTransition, useActionState, useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { ProductsPopover } from '../../(produtos)/components/products-popover';
import { filterFormValidationAction } from '../actions';

export default function FilterForm() {
  const { replace } = useRouter();
  const [state, filterAction] = useActionState(filterFormValidationAction, {});
  const hookForm = useForm<FilterFormType>({
    resolver: zodResolver(filterFormSchema),
    defaultValues: {
      search: '',
      type: '',
      startDate: '',
      endDate: '',
      productId: ''
    }
  });
  const { setError } = hookForm;

  async function onSubmit(data: FilterFormType) {
    const formData = objectToFormData(data);

    startTransition(() => {
      filterAction(formData);
    });
  }

  useEffect(() => {
    if (state?.errors) {
      Object.entries(state.errors).forEach(([field, messages]) => {
        setError(field as keyof FilterFormType, {
          type: 'server',
          message: messages[0]
        });
      });
    }

    if (state.success && state.data) {
      const urlParams = new URLSearchParams();

      Object.entries(state.data).forEach(([key, value]) => {
        if (value !== 'null' && value !== undefined && value !== null) {
          urlParams.set(key, value);
        } else {
          urlParams.delete(key);
        }
      });

      replace(`?${urlParams.toString()}`, { scroll: false });
    }
  }, [state]);

  return (
    <div>
      <Form {...hookForm}>
        <form onSubmit={hookForm.handleSubmit(onSubmit)}>
          <div className='flex gap-2.5 items-end flex-wrap'>
            <FormField
              control={hookForm.control}
              name='search'
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Cód. Movimentação</FormLabel>
                  <FormControl>
                    <Input
                      className='max-w-36'
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
              name='type'
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Tipo</FormLabel>
                  <FormControl>
                    <Select
                      onValueChange={field.onChange}
                      value={field.value}
                    >
                      <SelectTrigger className='max-w-36'>
                        <SelectValue />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value='ENTRADA'>ENTRADA</SelectItem>
                        <SelectItem value='SAIDA'>SAÍDA</SelectItem>
                        <SelectItem value='DEVOLUCAO'>DEVOLUÇÃO</SelectItem>
                        <SelectItem value='TRANSFERECIA'>
                          TRANSFERÊNCIA
                        </SelectItem>
                      </SelectContent>
                    </Select>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={hookForm.control}
              name='startDate'
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Data Inicial</FormLabel>
                  <FormControl>
                    <Input
                      className='max-w-36'
                      type='date'
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={hookForm.control}
              name='endDate'
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Data Final</FormLabel>
                  <FormControl>
                    <Input
                      className='max-w-36'
                      type='date'
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <CustomTooltip content='Limpar Filtro'>
              <Button
                type='button'
                onClick={hookForm.reset}
              >
                <EraserIcon />
              </Button>
            </CustomTooltip>
            <CustomTooltip content='Aplicar Filtro'>
              <Button>
                <SlidersHorizontalIcon />
              </Button>
            </CustomTooltip>
          </div>
        </form>
      </Form>
    </div>
  );
}
