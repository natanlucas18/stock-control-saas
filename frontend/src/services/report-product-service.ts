import { getApiUrl } from '@/lib/api-url';
import { getCookie } from '@/lib/get-token';
import { Product } from '@/types/product-schema';
import { ServerDTOArray } from '@/types/server-dto';

const localhost = getApiUrl();

export type ReportProductParams = {
  pageSize?: number;
  pageNumber?: number;
  sort?: string;
  status?: string;
};

export async function getReportProducts(
  params?: ReportProductParams
): Promise<ServerDTOArray<Product>> {
  const init: RequestInit = {
    cache: 'force-cache',
    headers: {
      Authorization: `Bearer ${await getCookie('accessToken')}`
    },
    next: { tags: ['report-products'], revalidate: 60 }
  };

  if (params) {
    const { sort = 'code', pageSize, pageNumber, status = '' } = params;
    const response = await fetch(
      `${localhost}/api/reports/products?sort=${sort}&size=${pageSize}&page=${pageNumber}&status=${status}`,
      init
    );
    const responseData = await response.json();
    return responseData;
  } else {
    {
      const response = await fetch(`${localhost}/api/reports/products`, init);
      const responseData = await response.json();
      return responseData;
    }
  }
}
