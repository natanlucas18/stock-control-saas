import { getAllStockLocations } from '@/app/requests/stock-location-request';
import { StockLocationsTable } from './local-estoque-table';

export default async function StockLocationsListPage({
  searchParams
}: {
  searchParams?: Promise<{ size?: string; page?: string }>;
}) {
  const params = await searchParams;
  const { data } = await getAllStockLocations({
    pageSize: params?.size,
    pageNumber: params?.page
  });
  const { content: locations, pagination } = data || {};

  return (
    <StockLocationsTable
      locations={locations}
      paginationOptions={pagination}
    />
  );
}
