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
import { Separator } from '@/components/ui/separator';
import {
  Sheet,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetTrigger
} from '@/components/ui/sheet';
import { returnBadgeComponent } from '@/lib/return-component';
import { Product } from '@/types/product-schema';
import { Edit3Icon } from 'lucide-react';
import { Fragment } from 'react';

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
      <SheetContent className='px-4 overflow-scroll'>
        <SheetHeader>
          <SheetTitle>Detalhes do Produto</SheetTitle>
        </SheetHeader>

        <Card>
          <CardContent className='space-y-2'>
            <img
              src='https://picsum.photos/400?grayscale&blur'
              alt='random image'
              className='rounded'
              width={400}
              height={400}
            />
            <div>
              <CardTitle className='text-[1.2rem]'>{product?.name}</CardTitle>
              <CardDescription>{product?.code}</CardDescription>
            </div>

            <p>R$ {product?.price}</p>
          </CardContent>
          <CardFooter>
            <CardAction>
              <Edit3Icon className='w-5 h-5 cursor-pointer' />
            </CardAction>
          </CardFooter>
        </Card>

        <Card>
          <CardHeader className='text-center'>
            <CardTitle className='text-[1.2rem]'>Estoque</CardTitle>
            <p>{`${product?.totalQuantity} - ${product?.unitMeasure}`}</p>
          </CardHeader>
          <CardContent>
            <div className='space-y-2'>
              <div className='flex justify-between'>
                <div>Status</div>
                <div>{returnBadgeComponent(product?.stockStatus)}</div>
              </div>
              <Separator />
              <div className='flex justify-between'>
                <div>Min</div>
                <div>{product?.stockMin}</div>
              </div>
              <Separator />
              <div className='flex justify-between'>
                <div>Max</div>
                <div>{product?.stockMax}</div>
              </div>
              <Separator />

              <div>
                <div className='mt-5 mb-2 text-center text-[1.2rem]'>
                  Localização
                </div>
                <div className='space-y-2'>
                  {product?.stockLocations.length === 0 ? (
                    <div className='text-center py-4 text-muted-foreground'>
                      Nenhuma localização cadastrada
                    </div>
                  ) : (
                    product?.stockLocations.map((location) => (
                      <Fragment key={location.id}>
                        <div className='flex justify-between'>
                          <div>{location.name}</div>
                          <div>{location.quantity}</div>
                        </div>
                        <Separator />
                      </Fragment>
                    ))
                  )}
                </div>
              </div>
            </div>
          </CardContent>
        </Card>
        <Card>
          <CardHeader>
            <CardTitle className='text-[1.2rem]'>Movimentações</CardTitle>
          </CardHeader>
          <CardContent>
            <div className='text-center py-4 text-muted-foreground'>
              Sem movimentações
            </div>
          </CardContent>
        </Card>
      </SheetContent>
    </Sheet>
  );
}
