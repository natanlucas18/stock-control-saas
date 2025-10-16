'use client';

import { useProducts } from '@/app/hooks/use-products';
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
import { useMemo, useState } from 'react';
import { useDebounce } from 'use-debounce';

type ProductsPopoverProps = {
  onChange: (value: number) => void;
};

export function ProductsPopover({ onChange }: ProductsPopoverProps) {
  const [selectedProduct, setSelectedProduct] = useState<Pick<
    ProductsData,
    'id' | 'name' | 'quantity'
  > | null>(null);
  const [open, setOpen] = useState(false);
  const [pageNumber, setPageNumber] = useState(1);

  const [search, setSearch] = useState('');
  const [searchValue] = useDebounce(search, 500);

  const params = useMemo(() => {
    return { pageNumber, search: searchValue, pageSize: 10 };
  }, [pageNumber, searchValue]);
  const { products, paginationOptions } = useProducts(params);

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
