'use client';

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
import { useProducts } from '@/hooks/use-products';
import { useStockLocation } from '@/hooks/use-stock-location';
import { getVisiblePages } from '@/lib/utils';
import { Product } from '@/types/product-schema';

import { PaginationOptions } from '@/types/server-dto';
import { StockLocationsData } from '@/types/stock-location-schema';
import { ChevronLeftIcon, ChevronRightIcon } from 'lucide-react';
import { useMemo, useState } from 'react';
import { useDebounce } from 'use-debounce';

type StockLocationPopoverProps = {
  onChange: (value: number) => void;
};

export function StockLocationPopover({ onChange }: StockLocationPopoverProps) {
  const [selectedStockLocation, setSelectedStockLocation] = useState<StockLocationsData | null>(null);
  const [open, setOpen] = useState(false);
  const [pageNumber, setPageNumber] = useState(1);

  const [search, setSearch] = useState('');
  const [searchValue] = useDebounce(search, 500);

  const params = useMemo(() => {
    return { pageNumber, search: searchValue, pageSize: 10 };
  }, [pageNumber, searchValue]);
  const { stockLocations, paginationOptions } = useStockLocation(params);

  return (
    <div>
      <Popover
        open={open}
        onOpenChange={setOpen}
      >
        <PopoverTrigger
          asChild
          className='w-full'
        >
          <Button
            variant='outline'
            className='justify-start'
          >
            {selectedStockLocation ? <>{selectedStockLocation.name}</> : <>Selecione</>}
          </Button>
        </PopoverTrigger>
        <PopoverContent
          className='p-0'
          side='bottom'
          align='start'
        >
          <Command>
            <CommandInput
              onValueChange={setSearch}
              placeholder='Change status...'
            />
            <CommandList>
              <CommandEmpty>Sem resultados.</CommandEmpty>
              <CommandGroup>
                {stockLocations.map((stockLocation) => (
                  <CommandItem
                    key={stockLocation.id}
                    value={stockLocation.name}
                    onSelect={() => {
                      setSelectedStockLocation(
                        stockLocation ||
                          null
                      );
                      onChange(stockLocation.id);
                      setOpen(false);
                    }}
                  >
                    <div className='w-24'>{stockLocation.name}</div>
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
            <ChevronRightIcon />
          </Button>
        </PaginationItem>
      </PaginationContent>
    </Pagination>
  );
}
