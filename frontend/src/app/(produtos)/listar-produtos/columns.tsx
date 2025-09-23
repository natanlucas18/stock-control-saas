import ProductEditForm from '@/components/product-edit-form';
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
import { Button } from '@/components/ui/button';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger
} from '@/components/ui/dialog';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger
} from '@/components/ui/dropdown-menu';
import { ProductsData } from '@/types/product-schema';
import { ColumnDef } from '@tanstack/react-table';
import { MoreHorizontal } from 'lucide-react';

export const columns: ColumnDef<ProductsData>[] = [
  {
    accessorKey: 'id',
    header: 'ID'
  },
  {
    accessorKey: 'name',
    header: 'Nome'
  },
  {
    accessorKey: 'price',
    header: () => <div>Preço</div>,
    cell: ({ row }) => {
      const price = parseFloat(row.getValue('price'));
      const formatted = new Intl.NumberFormat('pt-BR', {
        style: 'currency',
        currency: 'BRL'
      }).format(price);

      return <div className='font-medium'>{formatted}</div>;
    }
  },
  {
    accessorKey: 'stockMin',
    header: 'Estoque Min'
  },
  {
    accessorKey: 'stockMax',
    header: 'Estoque Max'
  },
  {
    accessorKey: 'unitMeasure',
    header: 'Unidade de Medida'
  },
  {
    accessorKey: 'stockLocationId',
    header: 'Local do Estoque'
  },
  {
    id: 'actions',
    cell: ({ row }) => {
      const product = row.original;

      return (
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button
              variant='ghost'
              className='h-8 w-8 p-0'
            >
              <span className='sr-only'>Open menu</span>
              <MoreHorizontal className='h-4 w-4' />
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align='end'>
            <DropdownMenuLabel>Actions</DropdownMenuLabel>
            <DropdownMenuItem
              onClick={() =>
                navigator.clipboard.writeText(product.id.toString())
              }
            >
              Copiar ID
            </DropdownMenuItem>
            <EditProductDialog product={product} />
            <DropdownMenuSeparator />
            <DeleteProductAlert productId={product.id} />
          </DropdownMenuContent>
        </DropdownMenu>
      );
    }
  }
];

function EditProductDialog({ product }: { product: ProductsData }) {
  return (
    <Dialog>
      <DialogTrigger>Editar</DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Editar Produto</DialogTitle>
        </DialogHeader>
        <ProductEditForm defaultValues={product} />
      </DialogContent>
    </Dialog>
  );
}

function DeleteProductAlert({ productId }: { productId: number }) {
  function onDelete(id: number) {
    console.log(`Produto ${id} deletado`);
  }

  return (
    <AlertDialog>
      <AlertDialogTrigger>Excluir</AlertDialogTrigger>
      <AlertDialogContent>
        <AlertDialogHeader>
          <AlertDialogTitle>Tem certeza que deseja excluir?</AlertDialogTitle>
        </AlertDialogHeader>
        <AlertDialogFooter>
          <AlertDialogCancel>Cancel</AlertDialogCancel>
          <AlertDialogAction onClick={() => onDelete(productId)}>
            Continue
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
