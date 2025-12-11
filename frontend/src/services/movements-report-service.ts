'use server';

import { getCookie } from '@/lib/get-token';
import {
  MovimentsReport,
  MovimentsReportParams
} from '@/types/moviments-report-schema';
import { ServerDTOArray } from '@/types/server-dto';

export async function getMovementsReportService(
  params?: MovimentsReportParams
): Promise<ServerDTOArray<MovimentsReport>> {
  const init: RequestInit = {
    cache: 'force-cache',
    headers: {
      Authorization: `Bearer ${await getCookie('accessToken')}`
    },
    next: { tags: ['movementsReport'], revalidate: 60 }
  };
  const url = new URL('http://localhost:8080/api/reports/movements');

  if (params) {
    const {
      startDate = '2025-08-01',
      endDate = '2025-09-30',
      productId,
      page,
      size,
      sort,
      type
    } = params;

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
