'use client';

import { columns } from '@/app/(produtos)/listar-produtos/columns';
import { DataTable } from '@/app/(produtos)/listar-produtos/data-table';
import { ProductsData } from '@/types/product-schema';

type ProductsTableProps = {
  products: ProductsData[];
};

export const products: ProductsData[] = [
  {
    id: 1,
    name: 'Produto 1',
    price: 12.5,
    stockMin: 4,
    stockMax: 10,
    unitMeasure: 'KG',
    stockLocationId: 2
  },
  {
    id: 2,
    name: 'Produto 2',
    price: 32.44,
    stockMin: 5,
    stockMax: 15,
    unitMeasure: 'UND',
    stockLocationId: 3
  },
  {
    id: 1,
    name: 'Produto 1',
    price: 12.5,
    stockMin: 4,
    stockMax: 10,
    unitMeasure: 'KG',
    stockLocationId: 2
  },
  {
    id: 2,
    name: 'Produto 2',
    price: 32.44,
    stockMin: 5,
    stockMax: 15,
    unitMeasure: 'UND',
    stockLocationId: 3
  },
  {
    id: 3,
    name: 'Produto 3',
    price: 12.5,
    stockMin: 4,
    stockMax: 10,
    unitMeasure: 'KG',
    stockLocationId: 4
  },
  {
    id: 4,
    name: 'Produto 4',
    price: 32.44,
    stockMin: 5,
    stockMax: 15,
    unitMeasure: 'UND',
    stockLocationId: 5
  }
];

export default function ProductsTable({ pageSize }: { pageSize?: number }) {
  return (
    <div>
      <DataTable
        columns={columns}
        data={products}
        pageSize={pageSize}
      />
    </div>
  );
}
