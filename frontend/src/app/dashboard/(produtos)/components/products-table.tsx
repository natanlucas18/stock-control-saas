'use client';

import { Button } from '@/components/ui/button';
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
  SelectSeparator,
  SelectTrigger,
  SelectValue
} from '@/components/ui/select';
import { Separator } from '@/components/ui/separator';
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
import { getProductById } from '@/services/products-service';
import { Product, ProductMin } from '@/types/product-schema';
import { PaginationOptions } from '@/types/server-dto';
import {
  ChevronDownIcon,
  ChevronLeftIcon,
  ChevronRightIcon,
  ChevronsUpDownIcon,
  ChevronUpIcon,
  MoreHorizontalIcon,
  SearchIcon
} from 'lucide-react';
import { useState } from 'react';
import { useDebouncedCallback } from 'use-debounce';
import ProductDeleteAlert from './product-delete-alert';
import ProductDetailsSheet from './product-details-sheet';
import ProductEditDialog from './product-edit-dialog';
import ProductRegisterDialog from './product-register-dialog';

type ProductsTableProps = {
  productsMin: ProductMin[];
  paginationOptions?: PaginationOptions;
  pageSize?: number;
};

export function ProductsTable({
  productsMin,
  paginationOptions
}: ProductsTableProps) {
  const { setUrlParam, params } = useUrlParams();
  const [openDetails, setOpenDetails] = useState(false);
  const [product, setProduct] = useState<Product>();

  async function getOneProduct(id: number) {
    const { data } = await getProductById(id);
    setProduct(data);
  }

  function handleSort(sort: string) {
    const currentSort = params.get('sort');
    const isSorted =
      currentSort !== `${sort},asc` || currentSort !== `${sort},desc`;
    if (isSorted) setUrlParam('sort', `${sort},asc`);

    switch (currentSort) {
      case `${sort},asc`:
        setUrlParam('sort', `${sort},desc`);
        break;
      case `${sort},desc`:
        setUrlParam('sort', '');
        break;
      case null:
        setUrlParam('sort', `${sort},asc`);
        break;
    }
  }

  function getSortIcon(sort: string) {
    const currentSort = params.get('sort');

    switch (currentSort) {
      case `${sort},asc`:
        return <ChevronUpIcon />;
      case `${sort},desc`:
        return <ChevronDownIcon />;
      default:
        return <ChevronsUpDownIcon />;
    }
  }

  return (
    <>
      <div className='flex justify-between'>
        <div className='flex gap-2.5'>
          <SearchInput />
          <PageSizeFilter />
        </div>
        <ProductRegisterDialog />
      </div>
      <Separator className='my-5' />
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead className='w-[100px]'>Código</TableHead>
            <TableHead
              className='cursor-pointer flex items-center gap-1'
              onClick={() => handleSort('name')}
            >
              Produto
              {getSortIcon('name')}
            </TableHead>
            <TableHead>Quantidade</TableHead>
            <TableHead className='text-left'>Unidade</TableHead>
            <TableHead className='text-left'>Status</TableHead>
            <TableHead className='text-left'>Ações</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {productsMin.length === 0 ? (
            <>
              <TableRow>
                <TableCell
                  colSpan={6}
                  className='text-center py-4 text-muted-foreground'
                >
                  Nenhum produto encontrado
                </TableCell>
              </TableRow>
            </>
          ) : (
            productsMin.map((productMin) => (
              <TableRow
                key={productMin.id}
                onDoubleClick={async () => {
                  await getOneProduct(productMin.id);
                  setOpenDetails(true);
                }}
                className='cursor-pointer'
              >
                <TableCell>{productMin.code}</TableCell>
                <TableCell>{productMin.name}</TableCell>
                <TableCell>{productMin.totalQuantity}</TableCell>
                <TableCell>{productMin.unitMeasure}</TableCell>
                <TableCell>{productMin.stockStatus}</TableCell>
                <TableCell
                  onDoubleClick={(e) => {
                    e.stopPropagation();
                  }}
                >
                  <ProductDropdownMenu productMin={productMin} />
                </TableCell>
              </TableRow>
            ))
          )}
        </TableBody>
      </Table>
      {paginationOptions && (
        <PaginationTable paginationOptions={paginationOptions} />
      )}

      <ProductDetailsSheet
        product={product}
        open={openDetails}
        onOpenChange={setOpenDetails}
      />
    </>
  );
}

function SearchInput() {
  const { setUrlParam } = useUrlParams();
  const debouncedState = useDebouncedCallback((value: string) => {
    setUrlParam('page', '1');
    setUrlParam('query', value);
  }, 500);

  function handleSearch(e: React.ChangeEvent<HTMLInputElement>) {
    debouncedState(e.target.value);
  }

  return (
    <div className='relative'>
      <SearchIcon className='absolute inset-y-0 left-0 flex place-self-center pointer-events-none pl-2' />
      <Input
        className='pl-7'
        placeholder='Pesquisar'
        onChange={handleSearch}
      />
    </div>
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
          <SelectSeparator />
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

type DropdownMenuProps = {
  productMin?: ProductMin;
  open?: boolean;
  onOpenChange?: (open: boolean) => void;
};

function ProductDropdownMenu({
  productMin,
  open,
  onOpenChange
}: DropdownMenuProps) {
  const [openEditDialog, setOpenEditDialog] = useState(false);
  const [openDeletAlert, setOpenDeletAlert] = useState(false);
  const [openDetails, setOpenDetails] = useState(false);
  const [product, setProduct] = useState<Product>();

  async function getOneProduct(id: number) {
    const { data } = await getProductById(id);
    setProduct(data);
  }

  return (
    <>
      <DropdownMenu
        open={open}
        onOpenChange={onOpenChange}
      >
        <DropdownMenuTrigger asChild>
          <Button
            variant='ghost'
            className='h-8 w-8 p-0'
          >
            <MoreHorizontalIcon className='h-4 w-4' />
          </Button>
        </DropdownMenuTrigger>
        <DropdownMenuContent
          align='end'
          className=''
        >
          <DropdownMenuLabel>Ações</DropdownMenuLabel>
          <DropdownMenuItem
            onClick={() =>
              navigator.clipboard.writeText(productMin?.code || '')
            }
          >
            Copiar Código
          </DropdownMenuItem>
          <DropdownMenuItem
            onClick={async () => {
              if (productMin) await getOneProduct(productMin.id);
              setOpenDetails(true);
            }}
          >
            Detalhes
          </DropdownMenuItem>
          <DropdownMenuItem
            onClick={async () => {
              if (productMin) await getOneProduct(productMin.id);
              setOpenEditDialog(true);
            }}
          >
            Editar
          </DropdownMenuItem>
          <DropdownMenuSeparator />
          <DropdownMenuItem
            variant='destructive'
            onClick={() => setOpenDeletAlert(true)}
          >
            Excluir
          </DropdownMenuItem>
        </DropdownMenuContent>
      </DropdownMenu>

      <ProductEditDialog
        product={product!}
        open={openEditDialog}
        onOpenChange={setOpenEditDialog}
      />

      <ProductDeleteAlert
        product={productMin}
        open={openDeletAlert}
        onOpenChange={setOpenDeletAlert}
      />

      <ProductDetailsSheet
        product={product}
        open={openDetails}
        onOpenChange={setOpenDetails}
      />
    </>
  );
}
