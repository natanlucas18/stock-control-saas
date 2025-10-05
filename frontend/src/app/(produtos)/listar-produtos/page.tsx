import { getAllProducts } from '@/app/requests/products-request';
import { ProductsTable } from '../components/products-table';

export default async function ProductsListPage({
  searchParams
}: {
  searchParams?: Promise<{ size?: number; page?: number }>;
}) {
  const params = await searchParams;
  const { data } = await getAllProducts({
    pageSize: params?.size,
    pageNumber: params?.page
  });
  const { content: products, pagination } = data || {};

  return (
    <ProductsTable
      products={products}
      paginationOptions={pagination}
    />
  );
}
