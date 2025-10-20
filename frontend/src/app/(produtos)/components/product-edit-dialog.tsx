'use client';

import ProductEditForm from '@/app/(produtos)/components/product-edit-form';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger
} from '@/components/ui/dialog';
import { ProductsData } from '@/types/product-schema';

export default function ProductEditDialog({
  product,
  open,
  onOpenChange,
  trigger
}: {
  product: ProductsData;
  open?: boolean;
  onOpenChange?: (open: boolean) => void;
  trigger?: React.ReactNode;
}) {
  return (
    <Dialog
      open={open}
      onOpenChange={onOpenChange}
    >
      <DialogTrigger className='cursor-pointer'>{trigger}</DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Editar Produto</DialogTitle>
        </DialogHeader>
        <ProductEditForm defaultValues={product} />
      </DialogContent>
    </Dialog>
  );
}
