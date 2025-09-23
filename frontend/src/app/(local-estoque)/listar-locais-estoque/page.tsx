import StockLocationsTable from '@/components/stock-locations-table';


export default async function StockLocationListPage() {
  return <StockLocationsTable pageSize={4} />;
}
