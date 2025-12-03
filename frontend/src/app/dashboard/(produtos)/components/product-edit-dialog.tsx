'use client';

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger
} from '@/components/ui/dialog';
import { Product } from '@/types/product-schema';
import ProductEditForm from './product-edit-form';

type ProductEditDialogProps = {
  product: Product;
  open?: boolean;
  onOpenChange?: (open: boolean) => void;
  trigger?: React.ReactNode;
};

export default function ProductEditDialog({
  product,
  open,
  onOpenChange,
  trigger
}: ProductEditDialogProps) {
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
          <DialogTitle>Editar Produto</DialogTitle>
        </DialogHeader>
        <ProductEditForm
          defaultValues={product}
          onOpenChange={onOpenChange}
        />
      </DialogContent>
    </Dialog>
  );
}
