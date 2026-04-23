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
import { useDeleteUnitMeasure } from '@/hooks/unit-measures/useDeleteUnitMeasure';
import { softUnitMeasureDelete } from '@/services/unit-measure-service';
import { useRouter } from 'next/navigation';
import { toast } from 'react-toastify';

type UnitMeasureDeleteAlertProps = {
  unitMeasureId?: number;
  open?: boolean;
  onOpenChange?: (open: boolean) => void;
  trigger?: React.ReactNode;
};

export default function UnitMeasureDeleteAlert({
  unitMeasureId,
  open,
  onOpenChange,
  trigger
}: UnitMeasureDeleteAlertProps) {
  const router = useRouter();

  const { mutate, isPending } = useDeleteUnitMeasure()

  async function onDelete(id: number) {
    mutate(id, {
      onSuccess: () => {
        toast.success('Unidade de medida excluída com sucesso');
        router.refresh();
        onOpenChange?.(false)
      },
      onError: () => {
        toast.error('Erro ao excluir unidade de medida');
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
            onClick={() => {
              if (unitMeasureId) onDelete(unitMeasureId);
            }}
            disabled={isPending}
          >
            {isPending ? 'Excluindo' : 'Continuar'}
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
