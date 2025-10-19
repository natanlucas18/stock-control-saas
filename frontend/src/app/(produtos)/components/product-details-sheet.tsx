'use client';

import {
  Sheet,
  SheetContent,
  SheetDescription,
  SheetHeader,
  SheetTitle
} from '@/components/ui/sheet';
import { ProductsData } from '@/types/product-schema';

type ProductDetailsSheetProps = {
  product?: ProductsData;
  open?: boolean;
  onOpenChange?: (open: boolean) => void;
};

export default function ProductDetailsSheet({
  product,
  open,
  onOpenChange
}: ProductDetailsSheetProps) {
  return (
    <Sheet
      open={open}
      onOpenChange={onOpenChange}
    >
      <SheetContent>
        <SheetHeader>
          <SheetTitle>{product?.name}</SheetTitle>
          <SheetDescription>
            <ul>
              <li>{product?.price}</li>
            </ul>
          </SheetDescription>
        </SheetHeader>
      </SheetContent>
    </Sheet>
  );
}
