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
import { softProductDelete } from '@/services/products-service';
import { ProductMin } from '@/types/product-schema';
import { useRouter } from 'next/navigation';
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
  async function onDelete(id: number) {
    const { success } = await softProductDelete(id);

    if (success) {
      toast.success('Produto excluído com sucesso');
      router.refresh();
    }

    if (!success) {
      toast.error('Erro ao excluir produto');
    }
  }

  return (
    <AlertDialog
      open={open}
      onOpenChange={onOpenChange}
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
          <AlertDialogCancel>Cancel</AlertDialogCancel>
          <AlertDialogAction
            onClick={() => {
              if (product) onDelete(product.id);
            }}
          >
            Continue
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
