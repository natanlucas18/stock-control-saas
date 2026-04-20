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
import { useDeleteStockLocation } from '@/hooks/stock-locations/useDeleteStockLocations';
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

  const { mutate, isPending } = useDeleteStockLocation()

  async function onDelete(id: number) {
    mutate(id, {
      onSuccess: () => {
        toast.success('Local de estoque excluído com sucesso');
        router.refresh();
        onOpenChange?.(false)
      },
      onError: () => {
        toast.error('Erro ao excluir local de estoque');
      }
    })
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
          <AlertDialogAction
            onClick={() => onDelete(location.id)}
            disabled={isPending}
          >
            {isPending ? 'Excluindo...' : 'Continuar'}
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
