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
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue
} from '@/components/ui/select';
import { useCreateProduct } from '@/hooks/products/useCreateProduct';
import { createProduct } from '@/services/products-service';
import { productFormSchema, ProductFormType } from '@/types/product-schema';
import { zodResolver } from '@hookform/resolvers/zod';
import { useRouter } from 'next/navigation';
import { useForm } from 'react-hook-form';
import { toast } from 'react-toastify';

type ProductRegisterProps = {
  onCloseDialog?: () => void
}

export default function ProductRegisterForm({ onCloseDialog }: ProductRegisterProps) {
  const router = useRouter();
  const hookForm = useForm<ProductFormType>({
    resolver: zodResolver(productFormSchema),
    defaultValues: {
      code: '',
      name: '',
      price: 0,
      stockMin: 0,
      stockMax: 0,
      unitMeasure: ''
    }
  });

  const { mutate, isPending } = useCreateProduct()

  async function onSubmit(formData: ProductFormType) {
    mutate(formData, {
      onSuccess: () => {
        toast.success('Produto cadastrado com sucesso!');
        hookForm.reset();
        router.refresh();
        onCloseDialog?.()
      },
      onError: () => {
        toast.error('Erro ao cadastrar produto!');
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
            name='code'
            render={({ field }) => (
              <FormItem>
                <FormLabel>Código</FormLabel>
                <FormControl>
                  <Input
                    type='text'
                    placeholder='BR-00000'
                    {...field}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />

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
            name='price'
            render={({ field }) => (
              <FormItem>
                <FormLabel>Preço</FormLabel>
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
            name='unitMeasure'
            render={({ field }) => (
              <FormItem>
                <FormLabel>Unidade de Medida</FormLabel>
                <FormControl>
                  <Select
                    onValueChange={field.onChange}
                    value={field.value}
                  >
                    <SelectTrigger className='w-[180px]'>
                      <SelectValue placeholder='Unidade de Medida' />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value='UN'>UN</SelectItem>
                      <SelectItem value='KG'>KG</SelectItem>
                    </SelectContent>
                  </Select>
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />

          <FormField
            control={hookForm.control}
            name='stockMin'
            render={({ field }) => (
              <FormItem>
                <FormLabel>Quantidade Mínima</FormLabel>
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
            name='stockMax'
            render={({ field }) => (
              <FormItem>
                <FormLabel>Quantidade Máxima</FormLabel>
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

          <Button 
            type='submit'
            disabled={isPending}
          >{isPending ? 'Salvando...' : 'Salvar Produto'}
          </Button>
        </form>
      </Form>
    </div>
  );
}
