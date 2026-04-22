'use client';

import { apiFetch } from '@/lib/api-client';
import { getApiUrl } from '@/lib/api-url';
import { createQueryParams } from '@/lib/create-query-params';
import { Params } from '@/types/Params';
import { ServerDTO, ServerDTOArray } from '@/types/server-dto';
import {
  StockLocationsData,
  StockLocationsFormType
} from '@/types/stock-location-schema';

const localhost = getApiUrl();

export async function createStockLocation(data: StockLocationsFormType) {
  return apiFetch<ServerDTO<StockLocationsData>>(`${localhost}/api/stock-locations`, {
    method: 'POST',
    body: JSON.stringify(data)
  })
}

export async function getAllStockLocations({ pageSize, pageNumber, search, sort }: Params) {
  const params = createQueryParams({
    size: pageSize,
    page: pageNumber,
    name: search,
    sort,
  })

  return apiFetch<ServerDTOArray<StockLocationsData>>(`${localhost}/api/stock-locations?${params.toString()}`, {
    method: 'GET',
  })
}


export async function updateStockLocation(
  id: number,
  data: StockLocationsFormType
) {
  return apiFetch<ServerDTO<StockLocationsData>>(`${localhost}/api/stock-locations/${id}`, {
    method: 'PUT',
    body: JSON.stringify(data)
  })
}

export async function deleteStockLocation(id: number) {
  return apiFetch<ServerDTO<StockLocationsData>>(`${localhost}/api/stock-locations/${id}`, {
    method: 'DELETE',
  })
}
