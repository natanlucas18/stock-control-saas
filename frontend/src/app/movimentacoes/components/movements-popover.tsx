'use client';

import { getAllProducts } from '@/app/requests/products-request';
import { Button } from '@/components/ui/button';
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList
} from '@/components/ui/command';
import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink
} from '@/components/ui/pagination';
import {
  Popover,
  PopoverContent,
  PopoverTrigger
} from '@/components/ui/popover';
import { getVisiblePages } from '@/lib/utils';
import { ProductsData } from '@/types/product-schema';
import { PaginationOptions } from '@/types/server-dto';
import { ChevronLeftIcon, ChevronRightIcon } from 'lucide-react';
import { useEffect, useState } from 'react';

type MovementsPopoverProps = {
  onChange: (value: number) => void;
};

export function MovementsPopover({ onChange }: MovementsPopoverProps) {
  const [open, setOpen] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState<Pick<
    ProductsData,
    'id' | 'name' | 'quantity'
  > | null>(null);
  const [pageNumber, setPageNumber] = useState(1);
  const [products, setProducts] = useState<ProductsData[]>([]);
  const [paginationOptions, setPaginationOptions] =
    useState<PaginationOptions>();
  const [search, setSearch] = useState(''); //estado que vai armazenar os dados no input search e setar na função getAllProducts({ search }). O setSeach será aplicado no onChageValue do Command

  useEffect(() => {
    const timeout = setTimeout(() => {
      getAllProducts({ pageNumber, search, pageSize: 10 }).then((response) => {
        setProducts(response.data.content);
        setPaginationOptions(response.data.pagination);
      });
    }, 400);
    return () => clearTimeout(timeout);
  }, [pageNumber, search]);

  return (
    <div>
      <Popover
        open={open}
        onOpenChange={setOpen}
      >
        <PopoverTrigger asChild>
          <Button
            variant='outline'
            className='w-[150px] justify-start'
          >
            {selectedProduct ? <>{selectedProduct.name}</> : <>Selecione</>}
          </Button>
        </PopoverTrigger>
        <PopoverContent
          className='p-0'
          side='right'
          align='start'
        >
          <Command>
            <CommandInput
              onValueChange={setSearch}
              placeholder='Change status...'
            />
            <CommandList>
              <CommandEmpty>No results found.</CommandEmpty>
              <CommandGroup>
                {products.map((product) => (
                  <CommandItem
                    key={product.id}
                    value={product.name}
                    onSelect={(name) => {
                      setSelectedProduct(
                        products.find((priority) => priority.name === name) ||
                          null
                      );
                      onChange(product.id);
                      setOpen(false);
                    }}
                  >
                    <div className='w-24'>{product.name}</div>
                    <div className='w-24'>{product.quantity}</div>
                  </CommandItem>
                ))}
              </CommandGroup>
            </CommandList>
          </Command>
          {paginationOptions && (
            <PaginationPopover
              paginationOptions={paginationOptions}
              onPageChange={setPageNumber}
            />
          )}
        </PopoverContent>
      </Popover>
    </div>
  );
}

type PaginationPopoverProps = {
  paginationOptions: PaginationOptions;
  onPageChange: (pageNumber: number) => void;
};

function PaginationPopover({
  paginationOptions,
  onPageChange
}: PaginationPopoverProps) {
  const { totalPages, first, last, pageNumber } = paginationOptions;
  const visiblePages = getVisiblePages(pageNumber, totalPages);

  return (
    <Pagination>
      <PaginationContent>
        <PaginationItem>
          <Button
            disabled={first}
            onClick={() => {
              onPageChange(pageNumber - 1);
            }}
          >
            <ChevronLeftIcon />
            Anterior
          </Button>
        </PaginationItem>

        {visiblePages.map((page, index) => (
          <PaginationItem key={index}>
            {typeof page === 'number' ? (
              <PaginationLink
                isActive={page === pageNumber}
                onClick={() => onPageChange(page)}
              >
                {page}
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
              onPageChange(pageNumber + 1);
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
