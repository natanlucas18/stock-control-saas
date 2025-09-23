import ProductsTable from '@/components/products-table';

export default async function ProductsListPage() {
  return <ProductsTable pageSize={4} />;
}
