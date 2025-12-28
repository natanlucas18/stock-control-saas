'use client';

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger
} from '@/components/ui/dialog';
import { UnitMeasureData } from '@/types/unit-measure-schema';
import UnitMeasureEditForm from './unit-measure-edit-form';

type UnitMeasureEditDialogProps = {
  unitMeasure: UnitMeasureData;
  open?: boolean;
  onOpenChange?: (open: boolean) => void;
  trigger?: React.ReactNode;
};

export default function UnitMeasureEditDialog({
  unitMeasure,
  open,
  onOpenChange,
  trigger
}: UnitMeasureEditDialogProps) {
  return (
    <Dialog
      open={open}
      onOpenChange={onOpenChange}
    >
      {trigger && (
        <DialogTrigger
          className='cursor-pointer'
          asChild
        >
          {trigger}
        </DialogTrigger>
      )}
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Editar unidade de medida</DialogTitle>
        </DialogHeader>
        <UnitMeasureEditForm
          defaultValues={unitMeasure}
          onOpenChange={onOpenChange}
        />
      </DialogContent>
    </Dialog>
  );
}
