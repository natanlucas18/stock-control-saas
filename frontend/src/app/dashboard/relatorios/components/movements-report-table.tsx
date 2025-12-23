'use client';

import CustomTooltip from '@/components/custom-tooltip';
import { Button } from '@/components/ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuTrigger
} from '@/components/ui/dropdown-menu';
import { Label } from '@/components/ui/label';
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
import { MovementsReport } from '@/types/movements-report-schema';
import { PaginationOptions } from '@/types/server-dto';
import {
  ChevronDownIcon,
  ChevronLeftIcon,
  ChevronRightIcon,
  ChevronsUpDownIcon,
  ChevronUpIcon,
  FileTextIcon,
  MoreHorizontalIcon
} from 'lucide-react';
import { useState } from 'react';
import FilterForm from './filter-form';
import { default as MovementsReportDetailsSheet } from './movements-report-details-sheet';

type MovementsReportTableProps = {
  repots: MovementsReport[];
  paginationOptions?: PaginationOptions;
  pageSize?: number;
};

export function MovementsReportTable({
  repots,
  paginationOptions
}: MovementsReportTableProps) {
  const { setUrlParam, params } = useUrlParams();
  const [openDetails, setOpenDetails] = useState(false);
  const [report, setReport] = useState<MovementsReport>();
  // const [report, setProduct] = useState<Product>();

  // async function getOneProduct(id: number) {
  //   const { data } = await getProductById(id);
  //   setProduct(data);
  // }

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
      <div className='flex justify-center'>
        <div className='flex gap-2.5 items-end flex-wrap'>
          <PageSizeFilter />
          <FilterForm />
          <CustomTooltip content='Gerar Relatório'>
            <Button>
              <FileTextIcon />
            </Button>
          </CustomTooltip>
        </div>
      </div>
      <Separator className='my-5' />
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead className='w-[100px]'>Código</TableHead>
            <TableHead className='text-left'>Tipo</TableHead>
            <CustomTooltip content='Quantidade'>
              <TableHead>Qtd.</TableHead>
            </CustomTooltip>
            <CustomTooltip content='Unidade de Medida'>
              <TableHead className='text-left'>U.M.</TableHead>
            </CustomTooltip>
            <TableHead
              className='text-left cursor-pointer flex items-center gap-1'
              onClick={() => handleSort('moment')}
            >
              Data {getSortIcon('moment')}
            </TableHead>
            <TableHead className='text-left'>Produto</TableHead>
            <TableHead className='text-left'>Usuário</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {repots.length === 0 ? (
            <>
              <TableRow>
                <TableCell
                  colSpan={7}
                  className='text-center py-4 text-muted-foreground'
                >
                  Nenhum relatório encontrado.
                </TableCell>
              </TableRow>
            </>
          ) : (
            repots.map((report) => (
              <TableRow
                key={report.id}
                onDoubleClick={async () => {
                  setOpenDetails(true);
                }}
                className='cursor-pointer'
              >
                <TableCell>{report.id}</TableCell>
                <TableCell>{report.type}</TableCell>
                <TableCell>{report.quantity}</TableCell>
                <TableCell>{report.unitMeasure}</TableCell>
                <TableCell>{report.moment}</TableCell>
                <TableCell>{report.product.name}</TableCell>
                <TableCell>{report.user.name}</TableCell>
                <TableCell
                  onDoubleClick={(e) => {
                    e.stopPropagation();
                  }}
                >
                  <MovementsDropdownMenu report={report} />
                </TableCell>
              </TableRow>
            ))
          )}
        </TableBody>
      </Table>
      {paginationOptions && (
        <PaginationTable paginationOptions={paginationOptions} />
      )}

      <MovementsReportDetailsSheet
        report={report}
        open={openDetails}
        onOpenChange={setOpenDetails}
      />
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
      <div className='grid gap-2'>
        <Label>Itens por Página</Label>
        <SelectTrigger className='max-w-36'>
          <SelectValue />
        </SelectTrigger>
      </div>
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
  report?: MovementsReport;
  open?: boolean;
  onOpenChange?: (open: boolean) => void;
};

function MovementsDropdownMenu({
  report,
  open,
  onOpenChange
}: DropdownMenuProps) {
  const [openDetails, setOpenDetails] = useState(false);

  // const [product, setProduct] = useState<Product>();
  // async function getOneProduct(id: number) {
  //   const { data } = await getProductById(id);
  //   setProduct(data);
  // }

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
              navigator.clipboard.writeText(report?.id.toString() ?? '')
            }
          >
            Copiar Código
          </DropdownMenuItem>
          <DropdownMenuItem
            onClick={async () => {
              setOpenDetails(true);
            }}
          >
            Detalhes
          </DropdownMenuItem>
        </DropdownMenuContent>
      </DropdownMenu>

      <MovementsReportDetailsSheet
        report={report}
        open={openDetails}
        onOpenChange={setOpenDetails}
      />
    </>
  );
}
