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
import { StockLocationsData } from '@/types/stock-location-schema';
import { ColumnDef } from '@tanstack/react-table';
import { MoreHorizontal } from 'lucide-react';
import StockLocationEditForm from '../components/stock-location-edit-form';

export const columns: ColumnDef<StockLocationsData>[] = [
  {
    accessorKey: 'id',
    header: 'ID'
  },
  {
    accessorKey: 'name',
    header: 'Nome'
  },
  {
    id: 'actions',
    cell: ({ row }) => {
      const stockLocation = row.original;

      return (
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button
              variant='ghost'
              className='h-8 w-8 p-0'
            >
              <span className='sr-only'>Abrir menu</span>
              <MoreHorizontal className='h-4 w-4' />
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align='end'>
            <DropdownMenuLabel>Ações</DropdownMenuLabel>
            <DropdownMenuItem
              onClick={() =>
                navigator.clipboard.writeText(stockLocation.id.toString())
              }
            >
              Copiar ID
            </DropdownMenuItem>
            <EditStockLocationDialog stockLocation={stockLocation} />
            <DropdownMenuSeparator />
            <DeleteProductAlert locationId={stockLocation.id} />
          </DropdownMenuContent>
        </DropdownMenu>
      );
    }
  }
];

function EditStockLocationDialog({
  stockLocation
}: {
  stockLocation: StockLocationsData;
}) {
  return (
    <Dialog>
      <DialogTrigger>Editar</DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Editar Local de Estoque</DialogTitle>
        </DialogHeader>
        <StockLocationEditForm defaultValues={stockLocation} />
      </DialogContent>
    </Dialog>
  );
}

function DeleteProductAlert({ locationId }: { locationId: number }) {
  function onDelete(id: number) {
    console.log(`local de estoque ${id} deletado`);
  }

  return (
    <AlertDialog>
      <AlertDialogTrigger>Excluir</AlertDialogTrigger>
      <AlertDialogContent>
        <AlertDialogHeader>
          <AlertDialogTitle>Tem certeza que deseja excluir?</AlertDialogTitle>
        </AlertDialogHeader>
        <AlertDialogFooter>
          <AlertDialogCancel>Cancelar</AlertDialogCancel>
          <AlertDialogAction onClick={() => onDelete(locationId)}>
            Continuar
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
