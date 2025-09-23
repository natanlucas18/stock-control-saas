'use client';

import { columns } from '@/app/(local-estoque)/listar-locais-estoque/columns';
import { DataTable } from '@/app/(produtos)/listar-produtos/data-table';
import { StockLocationsData } from '@/types/stock-location-schema';

type StockLocationsTableProps = {
  stockLocations: StockLocationsData[];
};

export const stockLocations: StockLocationsData[] = [
  {
    id: 1,
    name: 'Galpão 1',
  },
  {
    id: 2,
    name: 'Galpão 2',
  },
  {
    id: 3,
    name: 'Galpão 1',
  },
  {
    id: 4,
    name: 'Galpão 2',
  },
  {
    id: 5,
    name: 'Galpão 3',
  },
  {
    id: 6,
    name: 'Galpão 4',
  }
];

export default function StockLocationsTable({ pageSize }: { pageSize?: number }) {
  return (
    <div>
      <DataTable
        columns={columns}
        data={stockLocations}
        pageSize={pageSize}
      />
    </div>
  );
}
