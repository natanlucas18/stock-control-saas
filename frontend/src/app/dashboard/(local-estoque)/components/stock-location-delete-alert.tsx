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
import { deleteStockLocation } from '@/services/stock-location-service';
import { ProductsData } from '@/types/product-schema';
import { StockLocationsData } from '@/types/stock-location-schema';
import { useRouter } from 'next/navigation';
import { toast } from 'react-toastify';

export default function LocationDeleteAlert({
  location,
  open,
  onOpenChange,
  trigger
}: {
  location: Pick<StockLocationsData, 'id' | 'name'>;
  open?: boolean;
  onOpenChange?: (open: boolean) => void;
  trigger?: React.ReactNode;
}) {
  const router = useRouter();
  async function onDelete(id: number) {
    const { success } = await deleteStockLocation(id);

    if (success) {
      toast.success('Local de estoque excluído com sucesso');
      router.refresh();
    }

    if (!success) {
      toast.error('Erro ao excluir local de estoque');
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
            Tem certeza que deseja excluir?
          </AlertDialogTitle>
        </AlertDialogHeader>
        <AlertDialogFooter>
          <AlertDialogCancel>Cancelar</AlertDialogCancel>
          <AlertDialogAction onClick={() => onDelete(location.id)}>
            Continuar
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
