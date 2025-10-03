import StockLocationsTable from '@/app/(local-estoque)/components/stock-locations-table';

export default async function StockLocationListPage() {
  return <StockLocationsTable pageSize={4} />;
}
