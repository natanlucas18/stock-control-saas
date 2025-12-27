'use server';

import { getCookie } from '@/lib/get-token';
import {
  MovementsReport,
  MovementsReportParams
} from '@/types/movements-report-schema';
import { ServerDTOArray } from '@/types/server-dto';

export async function movementsReportFilterService(
  params?: MovementsReportParams
): Promise<ServerDTOArray<MovementsReport>> {
  const init: RequestInit = {
    cache: 'force-cache',
    headers: {
      Authorization: `Bearer ${await getCookie('accessToken')}`
    },
    next: { tags: ['movementsReport'], revalidate: 60 }
  };
  const url = new URL('http://localhost:8080/api/reports/movements');

  if (params) {
    const { startDate, endDate, productId, page, size, sort, type } = params;

    if (startDate) url.searchParams.set('startDate', startDate.toString());
    if (endDate) url.searchParams.set('endDate', endDate.toString());
    if (productId) url.searchParams.set('productId', productId.toString());
    if (page) url.searchParams.set('page', page.toString());
    if (size) url.searchParams.set('size', size.toString());
    if (sort) url.searchParams.set('sort', sort.toString());
    if (type) url.searchParams.set('type', type.toString());

    const response = await fetch(url, init);
    const responseData = await response.json();

    return responseData;
  } else {
    const response = await fetch(url, init);
    const responseData = await response.json();

    return responseData;
  }
}
