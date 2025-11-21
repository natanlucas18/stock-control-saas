'use client';

import {
  Card,
  CardAction,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle
} from '@/components/ui/card';
import {
  Sheet,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetTrigger
} from '@/components/ui/sheet';
import { Product } from '@/types/product-schema';

type ProductDetailsSheetProps = {
  product?: Product;
  open?: boolean;
  onOpenChange?: (open: boolean) => void;
  trigger?: React.ReactNode;
};

export default function ProductDetailsSheet({
  product,
  open,
  onOpenChange,
  trigger
}: ProductDetailsSheetProps) {
  return (
    <Sheet
      open={open}
      onOpenChange={onOpenChange}
    >
      {trigger && (
        <SheetTrigger
          className='cursor-pointer'
          asChild
        >
          {trigger}
        </SheetTrigger>
      )}
      <SheetContent className='px-4'>
        <SheetHeader>
          <SheetTitle>Detalhes do Produto</SheetTitle>
        </SheetHeader>
        <Card>
          <CardHeader>
            <CardTitle>{product?.name}</CardTitle>
            <CardDescription>Descrição do produto</CardDescription>
            <CardAction className='flex gap-2.5'>Action</CardAction>
          </CardHeader>
          <CardContent>
            <img
              src='https://picsum.photos/400?grayscale&blur'
              alt='random image'
              width={400}
              height={400}
            />
            <p>Preço: {product?.price}</p>
            <p>Quatidade: {product?.totalQuantity}</p>
          </CardContent>
          <CardFooter>
            <p>Card Footer</p>
          </CardFooter>
        </Card>
      </SheetContent>
    </Sheet>
  );
}
