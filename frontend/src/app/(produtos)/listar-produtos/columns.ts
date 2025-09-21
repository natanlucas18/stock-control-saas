import { ProductsData } from '@/types/product-schema';
import { ColumnDef } from '@tanstack/react-table';

export const columns: ColumnDef<ProductsData>[] = [
  {
    accessorKey: 'id',
    header: 'ID'
  },
  {
    accessorKey: 'name',
    header: 'Nome'
  },
  {
    accessorKey: 'price',
    header: 'Preço'
  },
  {
    accessorKey: 'stockMin',
    header: 'Estoque Min'
  },
  {
    accessorKey: 'stockMax',
    header: 'Estoque Max'
  },
  {
    accessorKey: 'unitMeasure',
    header: 'Unidade de Medida'
  },
  {
    accessorKey: 'stockLocationId',
    header: 'Local do Estoque'
  }
];
