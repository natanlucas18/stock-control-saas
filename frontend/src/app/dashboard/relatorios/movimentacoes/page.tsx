import { movementsReportFilterService } from '@/services/movements-report-service';
import { MovementsReportParams } from '@/types/movements-report-schema';
import { MovementsReportTable } from '../components/movements-report-table';

type UrlParams = { searchParams: Promise<MovementsReportParams> };

export default async function MovementsReport({ searchParams }: UrlParams) {
  const params = await searchParams;
  const { data } = await movementsReportFilterService(params);
  const content = data?.content ?? [];
  const pagination = data?.pagination ?? {};

  return (
    <MovementsReportTable
      repots={content}
      paginationOptions={pagination}
    />
  );
}
