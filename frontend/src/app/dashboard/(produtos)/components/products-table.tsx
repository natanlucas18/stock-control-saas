'use client';

import CustomTooltip from '@/components/custom-tooltip';
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
import { useProducts } from '@/hooks/products/useProducts';
import { useUrlParams } from '@/hooks/use-url-params';
import { parseNumber } from '@/lib/parse-number';
import { returnBadgeComponent } from '@/lib/return-component';
import { getVisiblePages } from '@/lib/utils';
import { getProductById } from '@/services/products-service';
import { Product, ProductMin } from '@/types/product-schema';
import { PaginationOptions } from '@/types/server-dto';
import {
  ArrowUpAZIcon,
  ArrowUpDownIcon,
  ArrowUpZAIcon,
  ChevronLeftIcon,
  ChevronRightIcon,
  ClipboardCopyIcon,
  MoreHorizontalIcon,
  SearchIcon
} from 'lucide-react';
import { Fragment, useState } from 'react';
import { FaExclamationCircle } from 'react-icons/fa';
import { useDebouncedCallback } from 'use-debounce';
import ProductDeleteAlert from './product-delete-alert';
import ProductDetailsSheet from './product-details-sheet';
import ProductEditDialog from './product-edit-dialog';
import ProductRegisterDialog from './product-register-dialog';

export function ProductsTable() {
  const { setUrlParam, params } = useUrlParams();
  const [openDetails, setOpenDetails] = useState(false);
  const [product, setProduct] = useState<Product>();

  const { data, isLoading } = useProducts({
    pageSize: parseNumber(params?.get('size')),
    pageNumber: parseNumber(params?.get('page')),
    search: params?.get('query') || undefined,
    sort: params?.get('sort') || undefined
  });

  const products = data?.data.content ?? [];
  const paginationOptions = data?.data.pagination;

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
        return <ArrowUpAZIcon />;
      case `${sort},desc`:
        return <ArrowUpZAIcon />;
      default:
        return <ArrowUpDownIcon />;
    }
  }

  return (
    <>
      <div className='md:flex md:gap-4'>
        <SearchInput />
        <div className='flex gap-4'>
          <div className='flex gap-1 place-items-center'>
            <PageSizeFilter />
            <CustomTooltip content='Quantidade de itens por página'>
              <FaExclamationCircle className='not-md:hidden' />
            </CustomTooltip>
          </div>
          <CustomTooltip content='Orden alfabética'>
            <Button
              onClick={() => handleSort('name')}
              className='w-fit'
            >
              {getSortIcon('name')}
            </Button>
          </CustomTooltip>
          <ProductRegisterDialog />
        </div>
      </div>
      <Separator className='my-5' />
      <div className='space-y-5 overflow-x-auto'>
        {products.length === 0 ? (
          <div>
            <div className='text-center py-4 text-muted-foreground'>
              Nenhum produto encontrado
            </div>
          </div>
        ) : (
          products.map((product) => (
            <Fragment key={product.id}>
              <div
                onDoubleClick={async () => {
                  await getOneProduct(product.id);
                  setOpenDetails(true);
                }}
                className='grid grid-cols-[auto_1fr] dark:bg-neutral-900 rounded-lg p-5 gap-4 md:grid-cols-[auto_1fr_auto_auto_auto_auto] md:gap-10 bg-neutral-100'
              >
                <div>
                  <img
                    src='https://picsum.photos/400?grayscale&blur'
                    alt='random image'
                    className='rounded'
                    width={64}
                    height={64}
                  />
                </div>
                <div>
                  <div className='text-[1.2rem]'>{product.name}</div>
                  <div className='flex gap-1 items-center dark:text-neutral-400'>
                    <div>{product.code}</div>
                    <ClipboardCopyIcon
                      onDoubleClick={(e) => e.stopPropagation()}
                      onClick={() =>
                        navigator.clipboard.writeText(product.code || '')
                      }
                      size={15}
                    />
                  </div>
                </div>
                <Separator
                  orientation='vertical'
                  className='not-md:hidden'
                />
                <Separator className='md:hidden col-span-2 md:col-span-1' />
                <div className='col-span-2 grid grid-cols-2 place-items-center md:col-span-1 md:gap-4'>
                  <div>
                    <div className='dark:text-neutral-400 text-[0.8rem]'>
                      QUANTIDADE
                    </div>
                    <div className='flex gap-2 justify-center'>
                      <div className='text-[1.2rem]'>
                        {product.totalQuantity}
                      </div>
                      <div className='text-[1.2rem]'>{product.unitMeasure}</div>
                    </div>
                  </div>
                  <div>
                    <div className='dark:text-neutral-400 text-center text-[0.8rem]'>
                      STATUS
                    </div>
                    <div>{returnBadgeComponent(product.stockStatus)}</div>
                  </div>
                </div>
                <Separator
                  orientation='vertical'
                  className='not-md:hidden'
                />
                <div
                  className='col-span-2 place-self-center border rounded-lg md:col-span-1'
                  onDoubleClick={(e) => {
                    e.stopPropagation();
                  }}
                >
                  <ProductDropdownMenu productMin={product} />
                </div>
              </div>
            </Fragment>
          ))
        )}
      </div>

      {paginationOptions && (
        <div className='mt-4'>
          <PaginationTable paginationOptions={paginationOptions} />
        </div>
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
    <div className='relative mb-4 md:mb-0'>
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
      <SelectTrigger className='w-auto'>
        <SelectValue />
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
        <DropdownMenuContent align='end'>
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
