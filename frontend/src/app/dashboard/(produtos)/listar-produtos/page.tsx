import { getAllProducts } from '@/app/services/products-service';
import { ProductsTable } from '../components/products-table';

type UrlParams = {
  searchParams: Promise<{
    sort?: string;
    size?: number;
    page?: number;
    name?: string;
  }>;
};

export default async function ProductsListPage({ searchParams }: UrlParams) {
  const params = await searchParams;
  const { data } = await getAllProducts({
    pageSize: params?.size,
    pageNumber: params?.page,
    search: params?.name,
    sort: params?.sort
  });
  const { content: products, pagination } = data;

  return (
    <ProductsTable
      products={products}
      paginationOptions={pagination}
    />
  );
}
