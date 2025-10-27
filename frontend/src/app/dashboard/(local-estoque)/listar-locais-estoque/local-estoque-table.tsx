'use client';

import { deleteStockLocation } from '@/app/services/stock-location-request';
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
import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink
} from '@/components/ui/pagination';
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue
} from '@/components/ui/select';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow
} from '@/components/ui/table';
import { useUrlParams } from '@/hooks/use-url-params';
import { getVisiblePages } from '@/lib/utils';
import { PaginationOptions } from '@/types/server-dto';
import { StockLocationsData } from '@/types/stock-location-schema';
import { Separator } from '@radix-ui/react-select';
import {
  ChevronLeftIcon,
  ChevronRightIcon,
  MoreHorizontal
} from 'lucide-react';
import { useRouter } from 'next/navigation';
import { toast } from 'react-toastify';
import StockLocationEditForm from '../components/stock-location-edit-form';

type StockLocationsTableProps = {
  locations: StockLocationsData[];
  paginationOptions?: PaginationOptions;
  pageSize?: number;
};

export function StockLocationsTable({
  locations,
  paginationOptions
}: StockLocationsTableProps) {
  return (
    <>
      <PageSizeFilter />
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead className='w-[100px]'>ID</TableHead>
            <TableHead>Nome</TableHead>
            <TableHead className='text-right'>Ações</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {locations.map((location) => (
            <TableRow key={location.id}>
              <TableCell>{location.id}</TableCell>
              <TableCell>{location.name}</TableCell>
              <TableCell>
                <ProductsDropdownMenu location={location} />
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
      {paginationOptions && (
        <PaginationTable paginationOptions={paginationOptions} />
      )}
    </>
  );
}

function PageSizeFilter() {
  const { setUrlParam } = useUrlParams();

  function handlePageSizeChange(pageSizeValue: string) {
    setUrlParam('size', pageSizeValue);
    setUrlParam('page', '1');
  }

  return (
    <Select onValueChange={handlePageSizeChange}>
      <SelectTrigger className='w-[180px]'>
        <SelectValue placeholder='Quantidade por Página' />
      </SelectTrigger>
      <SelectContent>
        <SelectGroup>
          <SelectLabel>Opções</SelectLabel>
          <Separator />
          <SelectItem value='5'>5</SelectItem>
          <SelectItem value='10'>10</SelectItem>
          <SelectItem value='15'>15</SelectItem>
          <SelectItem value='20'>20</SelectItem>
        </SelectGroup>
      </SelectContent>
    </Select>
  );
}

type PaginationTableProps = {
  paginationOptions: PaginationOptions;
};

function PaginationTable({ paginationOptions }: PaginationTableProps) {
  const { totalPages, first, last, pageNumber } = paginationOptions;
  const { setUrlParam } = useUrlParams();
  const visiblePages = getVisiblePages(pageNumber, totalPages);

  function handlePageChange(pageNumber: number) {
    setUrlParam('page', pageNumber.toString());
  }

  return (
    <Pagination>
      <PaginationContent>
        <PaginationItem>
          <Button
            disabled={first}
            onClick={() => {
              handlePageChange(pageNumber - 1);
            }}
            className='bg-gray-300'
          >
            <ChevronLeftIcon />
          </Button>
        </PaginationItem>

        {visiblePages.map((p, idx) => (
          <PaginationItem key={idx}>
            {typeof p === 'number' ? (
              <PaginationLink
                isActive={p === pageNumber}
                onClick={() => handlePageChange(p)}
              >
                {p}
              </PaginationLink>
            ) : (
              <PaginationEllipsis />
            )}
          </PaginationItem>
        ))}

        <PaginationItem>
          <Button
            disabled={last}
            onClick={() => {
              handlePageChange(pageNumber + 1);
            }}
            className='bg-gray-300'
          >
            <ChevronRightIcon />
          </Button>
        </PaginationItem>
      </PaginationContent>
    </Pagination>
  );
}

function ProductsDropdownMenu({ location }: { location: StockLocationsData }) {
  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button
          variant='ghost'
          className='h-8 w-8 p-0'
        >
          <MoreHorizontal className='h-4 w-4' />
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent align='end'>
        <DropdownMenuLabel>Actions</DropdownMenuLabel>
        <DropdownMenuItem
          onClick={() => navigator.clipboard.writeText(location.id.toString())}
        >
          Copiar ID
        </DropdownMenuItem>
        <EditStockLocationDialog location={location} />
        <DropdownMenuSeparator />
        <DeleteStockLocationAlert locationId={location.id} />
      </DropdownMenuContent>
    </DropdownMenu>
  );
}

function EditStockLocationDialog({
  location
}: {
  location: StockLocationsData;
}) {
  return (
    <Dialog>
      <DialogTrigger>Editar</DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Editar Produto</DialogTitle>
        </DialogHeader>
        <StockLocationEditForm defaultValues={location} />
      </DialogContent>
    </Dialog>
  );
}

function DeleteStockLocationAlert({ locationId }: { locationId: number }) {
  const router = useRouter();
  async function onDelete(id: number) {
    const { success } = await deleteStockLocation(id);

    if (success) {
      toast.success('Local de estoque excluído com sucesso');
      router.refresh();
    }

    if (!success) {
      toast.error('Erro ao excluir local de estoque');
    }
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
          <AlertDialogAction onClick={() => onDelete(locationId)}>
            Continuar
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
