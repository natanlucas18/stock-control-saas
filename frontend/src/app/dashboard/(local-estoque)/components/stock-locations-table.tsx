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
import { PaginationOptions } from '@/types/server-dto';
import { StockLocationsData } from '@/types/stock-location-schema';
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
import LocationEditDialog from './stock-location-edit-dialog';
import LocationDeleteAlert from './stock-location-delete-alert';
import StockLocationRegisterDialog from './stock-location-register-dialog';
import {CiEdit} from 'react-icons/ci'
import {RiDeleteBin5Line} from 'react-icons/ri'

export type StockLocationsTableProps = {
  locations: StockLocationsData[];
  paginationOptions?: PaginationOptions;
  pageSize?: number;
};

const initialValues: StockLocationsData= {
  id: 1,
  name: ''
};

export function StockLocationsTable({
  locations,
  paginationOptions
}: StockLocationsTableProps) {
  const { setUrlParam, params } = useUrlParams();

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
        <StockLocationRegisterDialog />
      </div>
      <Separator className='my-5' />
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead
              className='cursor-pointer flex items-center gap-1'
              onClick={() => handleSort('name')}
            >
              Nome
              {getSortIcon('name')}
            </TableHead>
            <TableHead className='text-left '>Ações</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {locations?.map((location) => (
            <TableRow
              key={location.id}
              className='cursor-pointer'
            >
              <TableCell>{location.name}</TableCell>
              <TableCell
                onDoubleClick={(e) => {
                  e.stopPropagation();
                }}
              >
                <StockLocationDropdownMenu location={location} />
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
    setUrlParam('page', '1');
    setUrlParam('name', value);
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

function StockLocationDropdownMenu({
  location,
  open,
  onOpenChange
}: {
  location: StockLocationsData;
  open?: boolean;
  onOpenChange?: (open: boolean) => void;
}) {
  const [openEditDialog, setOpenEditDialog] = useState(false);
  const [openDeletAlert, setOpenDeletAlert] = useState(false);

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
          <DropdownMenuItem onClick={() => setOpenEditDialog(true)}>
            <CiEdit/>
            Editar
          </DropdownMenuItem>
          <DropdownMenuSeparator />
          <DropdownMenuItem
            variant='destructive'
            onClick={() => setOpenDeletAlert(true)}
          >
            <RiDeleteBin5Line/>
            Excluir
          </DropdownMenuItem>
        </DropdownMenuContent>
      </DropdownMenu>

      <LocationEditDialog
        location={location}
        open={openEditDialog}
        onOpenChange={setOpenEditDialog}
      />

      <LocationDeleteAlert
        location={location}
        open={openDeletAlert}
        onOpenChange={setOpenDeletAlert}
      />
    </>
  );
}
