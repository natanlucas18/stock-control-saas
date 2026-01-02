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
import { UnitMeasureData } from '@/types/unit-measure-schema';
import { getUnitMeasureById } from '@/services/unit-measure-service';
import UnitMeasureDeleteAlert from './unit-measure-delete-alert';
import UnitMeasureEditDialog from './unit-measure-edit-dialog';
import UnitMeasureRegisterDialog from './unit-measure-register-dialog';

type UnitMeasureTableProps = {
  unitsMeasure: UnitMeasureData[];
  paginationOptions?: PaginationOptions;
  pageSize?: number;
};

export function UnitMeasureTable({
  unitsMeasure,
  paginationOptions
}: UnitMeasureTableProps) {
  const { setUrlParam, params } = useUrlParams();
  const [unitMeasure, setUnitMeasure] = useState<UnitMeasureData>();

  async function getOneUnitMeasure(id: number) {
    const { data } = await getUnitMeasureById(id);
    setUnitMeasure(data);
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
      <div className='flex flex-col gap-4 lg:flex-row lg:items-center lg:justify-between mt-10'>
        <div className='flex flex-col gap-2 lg:flex-row lg:gap-6'>
          <UnitMeasureSearchInput/>
          <UnitMeasurePageSizeFilter/>
        </div>
        <UnitMeasureRegisterDialog/>
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
            <TableHead
              className='cursor-pointer flex items-center gap-1'
              onClick={() => handleSort('acronym')}
            >
              Sigla
            </TableHead>
            <TableHead>Ações</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {unitsMeasure.length === 0 ? (
            <>
              <TableRow>
                <TableCell
                  colSpan={6}
                  className='text-center py-4 text-muted-foreground'
                >
                  Nenhum resultado encontrado.
                </TableCell>
              </TableRow>
            </>
          ) : (
            unitsMeasure.map((unitMeasure) => (
              <TableRow
                key={unitMeasure.id}
                className='cursor-pointer'
              >
                <TableCell>{unitMeasure.name}</TableCell>
                <TableCell>{unitMeasure.acronym}</TableCell>
                <TableCell>
                  <ProductDropdownMenu unitMeasure={unitMeasure} />
                </TableCell>
              </TableRow>
            ))
          )}
        </TableBody>
      </Table>
      {paginationOptions && (
        <PaginationTable paginationOptions={paginationOptions} />
      )}
    </>
  );
}

function UnitMeasureSearchInput() {
  const { setUrlParam } = useUrlParams();
  const debouncedState = useDebouncedCallback((value: string) => {
    setUrlParam('page', '1');
    setUrlParam('query', value);
  }, 500);

  function handleSearch(e: React.ChangeEvent<HTMLInputElement>) {
    debouncedState(e.target.value);
  }

  return (
    <div className='relative w-72 lg:w-75'>
      <SearchIcon className='absolute inset-y-0 left-0 flex place-self-center pointer-events-none pl-2' />
      <Input
        className='pl-7'
        placeholder='Pesquisar'
        onChange={handleSearch}
      />
    </div>
  );
}

function UnitMeasurePageSizeFilter() {
  const { setUrlParam } = useUrlParams();

  function handlePageSizeChange(pageSizeValue: string) {
    setUrlParam('size', pageSizeValue);
    setUrlParam('page', '1');
  }

  return (
    <Select onValueChange={handlePageSizeChange}>
      <SelectTrigger className='w-38 lg:w-42'>
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
            Próxima
            <ChevronRightIcon />
          </Button>
        </PaginationItem>
      </PaginationContent>
    </Pagination>
  );
}

type DropdownMenuProps = {
  unitMeasure?: UnitMeasureData;
  open?: boolean;
  onOpenChange?: (open: boolean) => void;
};

function ProductDropdownMenu({
  unitMeasure,
  open,
  onOpenChange
}: DropdownMenuProps) {
  const [openEditDialog, setOpenEditDialog] = useState(false);
  const [openDeletAlert, setOpenDeletAlert] = useState(false);
  const [unitM, setUnitM] = useState<UnitMeasureData>();

  async function getOneProduct(id: number) {
    const { data } = await getUnitMeasureById(id);
    setUnitM (data);
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
            onClick={async () => {
              if (unitM) await getOneProduct(unitM.id);
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

      <UnitMeasureEditDialog
        unitMeasure={unitM!}
        open={openEditDialog}
        onOpenChange={setOpenEditDialog}
      />

      <UnitMeasureDeleteAlert
        unitMeasureId={unitM?.id}
        open={openDeletAlert}
        onOpenChange={setOpenDeletAlert}
      />
    </>
  );
}
