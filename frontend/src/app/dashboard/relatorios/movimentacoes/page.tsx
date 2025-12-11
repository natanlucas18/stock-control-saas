import { getMovementsReportService } from '@/services/movements-report-service';
import { MovimentsReportParams } from '@/types/moviments-report-schema';
import { MovementsReportTable } from '../components/moviments-report-table';

type UrlParams = { searchParams: Promise<MovimentsReportParams> };

export default async function MovimentsReport({ searchParams }: UrlParams) {
  const params = await searchParams;
  const { data } = await getMovementsReportService(params);
  const content = data?.content ?? [];
  const pagination = data?.pagination ?? {};

  return (
    <MovementsReportTable
      repots={content}
      paginationOptions={pagination}
    />
  );
}
