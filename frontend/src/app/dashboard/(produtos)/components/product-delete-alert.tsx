'use client';

import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger
} from '@/components/ui/alert-dialog';
import { useDeleteProduct } from '@/hooks/products/useDeleteProduct';
import { softProductDelete } from '@/services/products-service';
import { ProductMin } from '@/types/product-schema';
import { useRouter } from 'next/navigation';
import { useState } from 'react';
import { toast } from 'react-toastify';

type ProductDeleteAlertProps = {
  product?: Pick<ProductMin, 'id' | 'name'>;
  open?: boolean;
  onOpenChange?: (open: boolean) => void;
  trigger?: React.ReactNode;
};

export default function ProductDeleteAlert({
  product,
  open,
  onOpenChange,
  trigger
}: ProductDeleteAlertProps) {
  const router = useRouter();
  const [openDialog, setOpenDialog] = useState(false)

  const { mutate, isPending } = useDeleteProduct()

  async function onDelete(id: number) {
    mutate(id, {
      onSuccess: () => {
        toast.success('Produto excluído com sucesso');
        router.refresh();
        onOpenChange?.(false)
        setOpenDialog(false)
      },
      onError: () => {
        toast.error('Erro ao excluir produto');
      }
    })
  }

  return (
    <AlertDialog
      open={openDialog}
      onOpenChange={setOpenDialog}
    >
      <AlertDialogTrigger className='cursor-pointer'>
        {trigger}
      </AlertDialogTrigger>
      <AlertDialogContent>
        <AlertDialogHeader>
          <AlertDialogTitle>
            Tem certeza que deseja excluir o produto{' '}
            <span className='text-yellow-400'>{product?.name}</span>?
          </AlertDialogTitle>
        </AlertDialogHeader>
        <AlertDialogFooter>
          <AlertDialogCancel>Cancelar</AlertDialogCancel>
          <AlertDialogAction
            onClick={() => {
              if (product) onDelete(product.id);
            }}
            disabled={isPending}
          >
            { isPending ? 'Excluindo...': 'Continuar'}
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
