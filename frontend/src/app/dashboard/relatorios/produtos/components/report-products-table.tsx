'use client';

import { Button } from '@/components/ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
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
import { returnBadgeComponent } from '@/lib/return-component';
import { getVisiblePages } from '@/lib/utils';
import { getProductById } from '@/services/products-service';
import { Product } from '@/types/product-schema';
import { PaginationOptions } from '@/types/server-dto';
import {
  ChevronDownIcon,
  ChevronLeftIcon,
  ChevronRightIcon,
  ChevronsUpDownIcon,
  ChevronUpIcon,
  MoreHorizontalIcon,
} from 'lucide-react';
import { useState } from 'react';
import ProductDetailsSheet from '@/app/dashboard/(produtos)/components/product-details-sheet';
import { ReportProductsFormFilters } from './report-products-form-filters';

type ReportProductsTableProps = {
  products: Product[];
  paginationOptions: PaginationOptions;
  pageSize?: number;
};

export function ReportsProductsTable({
  products,
  paginationOptions
}: ReportProductsTableProps) {
  const { setUrlParam, params } = useUrlParams();
  const [openDetails, setOpenDetails] = useState(false);
  const [product, setProduct] = useState<Product>();
  const {totalElements} = paginationOptions;

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
      <ReportProductsFormFilters />
      <Separator className='my-5' />
      <div className='flex justify-between items-center'>
      <PageSizeFilter />
      <p className='mb-2 text-sm lg:text-md'>{totalElements} registros encontrados.</p>
      </div>
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
            <TableHead className='text-left'>Preço</TableHead>
            <TableHead className='text-left'>Estoque Min</TableHead>
            <TableHead className='text-left'>Estoque Max</TableHead>
            <TableHead className='text-left'>Status</TableHead>
            <TableHead className='text-left'>Ações</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {products.length === 0 ? (
            <>
              <TableRow>
                <TableCell
                  colSpan={10}
                  className='text-center py-4 text-muted-foreground'
                >
                  Nenhum resultado encontrado.
                </TableCell>
              </TableRow>
            </>
          ) : (
            products.map((product) => (
              <TableRow
                key={product.id}
                onDoubleClick={async () => {
                  await getOneProduct(product.id);
                  setOpenDetails(true);
                }}
                className='cursor-pointer'
              >
                <TableCell>{product.code}</TableCell>
                <TableCell>{product.name}</TableCell>
                <TableCell>{product.totalQuantity}</TableCell>
                <TableCell>{product.unitMeasure}</TableCell>
                <TableCell>{product.price.toLocaleString('pt-BR', {
                  style: 'currency',
                  currency: 'BRL',
                })}</TableCell>
                <TableCell>{product.stockMin}</TableCell>
                <TableCell>{product.stockMax}</TableCell>
                <TableCell>
                  {returnBadgeComponent(product.stockStatus)}
                </TableCell>
                <TableCell
                  onDoubleClick={(e) => {
                    e.stopPropagation();
                  }}
                >
                  <ProductDropdownMenu productMin={product} />
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




type PaginationTableProps = {
  paginationOptions: PaginationOptions;
};

function PaginationTable({ paginationOptions }: PaginationTableProps) {
  const { totalPages, first, last, pageNumber, totalElements } = paginationOptions;
  const { setUrlParam } = useUrlParams();
  const visiblePages = getVisiblePages(pageNumber, totalPages);

  function handlePageChange(pageNumber: number) {
    setUrlParam('page', pageNumber.toString());
  }

  return (
    <Pagination>
      <PaginationContent className='mt-2'>
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

function PageSizeFilter() {
  const { setUrlParam } = useUrlParams();

  function handlePageSizeChange(pageSizeValue: string) {
    setUrlParam('size', pageSizeValue);
    setUrlParam('page', '1');
  }  
  return (
    <Select onValueChange={handlePageSizeChange}>
      <SelectTrigger className='w-40 mb-2'>
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


export type DropdownMenuProps = {
  productMin?: Product;
  open?: boolean;
  onOpenChange?: (open: boolean) => void;
};

function ProductDropdownMenu({
  productMin,
  open,
  onOpenChange
}: DropdownMenuProps) {
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
        >
          <DropdownMenuLabel>Ações</DropdownMenuLabel>
          <DropdownMenuItem
            onClick={async () => {
              if (productMin) await getOneProduct(productMin.id);
              setOpenDetails(true);
            }}
          >
            Detalhes
          </DropdownMenuItem>
        </DropdownMenuContent>
      </DropdownMenu>

      <ProductDetailsSheet
        product={product}
        open={openDetails}
        onOpenChange={setOpenDetails}
      />
    </>
  );
}
