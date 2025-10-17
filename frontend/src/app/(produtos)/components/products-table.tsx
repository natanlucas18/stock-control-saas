'use client';

import ProductEditForm from '@/app/(produtos)/components/product-edit-form';
import { useUrlParams } from '@/app/hooks/use-url-params';
import { softProductDelete } from '@/app/services/products-service';
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle
} from '@/components/ui/alert-dialog';
import { Button } from '@/components/ui/button';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle
} from '@/components/ui/dialog';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger
} from '@/components/ui/dropdown-menu';
import { Input } from '@/components/ui/input';
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
import { getVisiblePages } from '@/lib/utils';
import { ProductsData } from '@/types/product-schema';
import { PaginationOptions } from '@/types/server-dto';
import { Separator } from '@radix-ui/react-select';
import {
  ChevronLeftIcon,
  ChevronRightIcon,
  MoreHorizontal
} from 'lucide-react';
import { useRouter } from 'next/navigation';
import { useState } from 'react';
import { toast } from 'react-toastify';
import { useDebouncedCallback } from 'use-debounce';

type ProductsTableProps = {
  products: ProductsData[];
  paginationOptions?: PaginationOptions;
  pageSize?: number;
};

export function ProductsTable({
  products,
  paginationOptions
}: ProductsTableProps) {
  return (
    <>
      <PageSizeFilter />
      <SearchInput />
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead className='w-[100px]'>Código</TableHead>
            <TableHead>Produto</TableHead>
            <TableHead>Quantidade</TableHead>
            <TableHead className='text-left'>Unidade</TableHead>
            <TableHead className='text-left'>Status</TableHead>
            <TableHead className='text-left'>Ações</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {products.map((product) => (
            <TableRow key={product.id}>
              <TableCell>{product.code}</TableCell>
              <TableCell>{product.name}</TableCell>
              <TableCell>{product.quantity}</TableCell>
              <TableCell>{product.unitMeasure}</TableCell>
              <TableCell>{product.stockStatus.message}</TableCell>
              <TableCell>
                <ProductsDropdownMenu product={product} />
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

function SearchInput() {
  const { setUrlParam } = useUrlParams();
  const debouncedState = useDebouncedCallback((value: string) => {
    setUrlParam('name', value);
  }, 500);

  function handleSearch(e: React.ChangeEvent<HTMLInputElement>) {
    debouncedState(e.target.value);
  }

  return (
    <Input
      placeholder='Pesquisar'
      onChange={handleSearch}
    />
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
          >
            <ChevronLeftIcon />
            Anterior
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
          >
            Próximo
            <ChevronRightIcon />
          </Button>
        </PaginationItem>
      </PaginationContent>
    </Pagination>
  );
}

function ProductsDropdownMenu({ product }: { product: ProductsData }) {
  const [openEditDialog, setOpenEditDialog] = useState(false);
  const [openDeletAlert, setOpenDeletAlert] = useState(false);

  return (
    <>
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
          <DropdownMenuLabel>Ações</DropdownMenuLabel>
          <DropdownMenuItem
            onClick={() => navigator.clipboard.writeText(product.code)}
          >
            Copiar Código
          </DropdownMenuItem>
          <DropdownMenuItem onSelect={() => setOpenEditDialog(true)}>
            Editar
          </DropdownMenuItem>
          <DropdownMenuSeparator />
          <DropdownMenuItem
            variant='destructive'
            onSelect={() => setOpenDeletAlert(true)}
          >
            Excluir
          </DropdownMenuItem>
        </DropdownMenuContent>
      </DropdownMenu>

      <DeleteProductAlert
        productId={product.id}
        open={openDeletAlert}
        onOpenChange={setOpenDeletAlert}
      />
      <EditProductDialog
        product={product}
        open={openEditDialog}
        onOpenChange={setOpenEditDialog}
      />
    </>
  );
}

function EditProductDialog({
  product,
  open,
  onOpenChange
}: {
  product: ProductsData;
  open: boolean;
  onOpenChange: (open: boolean) => void;
}) {
  return (
    <Dialog
      open={open}
      onOpenChange={onOpenChange}
    >
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Editar Produto</DialogTitle>
        </DialogHeader>
        <ProductEditForm defaultValues={product} />
      </DialogContent>
    </Dialog>
  );
}

function DeleteProductAlert({
  productId,
  open,
  onOpenChange
}: {
  productId: number;
  open: boolean;
  onOpenChange: (open: boolean) => void;
}) {
  const router = useRouter();
  async function onDelete(id: number) {
    const { success } = await softProductDelete(id);

    if (success) {
      toast.success('Produto excluído com sucesso');
      router.refresh();
    }

    if (!success) {
      toast.error('Erro ao excluir produto');
    }
  }

  return (
    <AlertDialog
      open={open}
      onOpenChange={onOpenChange}
    >
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
