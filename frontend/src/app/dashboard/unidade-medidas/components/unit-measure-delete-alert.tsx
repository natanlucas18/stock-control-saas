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
import { softUnitMeasureDelete } from '@/services/unit-measure-service';
import { UnitMeasureData } from '@/types/unit-measure-schema';
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
  async function onDelete(id: number) {
    const { success } = await softUnitMeasureDelete(id);

    if (success) {
      toast.success('Unidade de medida excluída com sucesso');
      router.refresh();
    }

    if (!success) {
      toast.error('Erro ao excluir unidade de medida');
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
          <AlertDialogAction
            onClick={() => {
              if (unitMeasureId) onDelete(unitMeasureId);
            }}
          >
            Continuar
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
