'use client';

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger
} from '@/components/ui/dialog';
import StockLocationEditForm from './stock-location-edit-form';
import { StockLocationsData } from '@/types/stock-location-schema';

export default function LocationEditDialog({
  location,
  open,
  onOpenChange,
  trigger
}: {
  location: StockLocationsData;
  open?: boolean;
  onOpenChange?: (open: boolean) => void;
  trigger?: React.ReactNode;
}) {
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
          <DialogTitle>Editar Local de Estoque</DialogTitle>
        </DialogHeader>
        <StockLocationEditForm defaultValues={location} />
      </DialogContent>
    </Dialog>
  );
}
