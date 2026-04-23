'use client'
import { apiFetch } from '@/lib/api-client';
import { getApiUrl } from '@/lib/api-url';
import { createQueryParams } from '@/lib/create-query-params';
import { getCookie } from '@/lib/get-token';
import { Product } from '@/types/product-schema';
import { ServerDTOArray } from '@/types/server-dto';

const localhost = getApiUrl();

export type ReportProductParams = {
  pageSize?: number | undefined;
  pageNumber?: number | undefined;
  sort?: string | undefined;
  status?: string | undefined;
};

export async function getReportProducts(
  {pageNumber, pageSize, sort, status}: ReportProductParams
) {
  const params = createQueryParams({
    page: pageNumber,
    size: pageSize,
    sort,
    status
  })
  return apiFetch<ServerDTOArray<Product>>(
    `${localhost}/api/reports/products?${params.toString()}`, {
    method: 'GET'
  })

}
