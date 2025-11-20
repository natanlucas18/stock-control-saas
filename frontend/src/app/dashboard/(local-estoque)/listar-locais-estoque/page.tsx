import { getAllStockLocations } from '@/services/stock-location-service';
import {StockLocationsTable} from '../components/stock-locations-table';

export default async function StockLocationsListPage({
  searchParams
}: {
  searchParams?: Promise<{ size?: string; page?: string, name?: string, sort?: string }>;
}) {
  const params = await searchParams;
  const { data } = await getAllStockLocations({
    pageSize: params?.size,
    pageNumber: params?.page,
    search: params?.name,
    sort: params?.sort
  });
  const { content: locations, pagination } = data || {};

  return (
    <StockLocationsTable
      locations={locations}
      paginationOptions={pagination}
    />
  );
}
