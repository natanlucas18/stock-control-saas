'use client';

import { apiFetch } from '@/lib/api-client';
import { getApiUrl } from '@/lib/api-url';
import { createQueryParams } from '@/lib/create-query-params';
import {
  MovementsReport,
  MovementsReportParams
} from '@/types/movements-report-schema';
import { ServerDTOArray } from '@/types/server-dto';

const localhost = getApiUrl();

export async function movementsReportFilterService(
  {startDate, endDate, productId, page, size, search, sort, type }: MovementsReportParams
){
  const params = createQueryParams({
    startDate,
    endDate,
    productId,
    page,
    size,
    search,
    sort,
    type
  })
  return apiFetch<ServerDTOArray<MovementsReport>>(
    `${localhost}/api/reports/movements?${params.toString()}`, {
    method: 'GET',
  })
}
