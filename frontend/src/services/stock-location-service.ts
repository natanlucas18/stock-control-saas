'use server';

import { getApiUrl } from '@/lib/api-url';
import { getToken } from '@/lib/get-token';
import { ServerDTO, ServerDTOArray } from '@/types/server-dto';
import {
  StockLocationParams,
  StockLocationsData,
  StockLocationsFormType
} from '@/types/stock-location-schema';
import { revalidateTag } from 'next/cache';

const localhost = getApiUrl();

export async function createStockLocations(data: StockLocationsFormType) {
  const response = await fetch(`${localhost}/api/stock-locations`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${await getToken()}`
    },
    body: JSON.stringify(data)
  });

  revalidateTag('locations');

  const responseData = await response.json();

  return responseData as ServerDTO<StockLocationsData>;
}

export async function getAllStockLocations(params?: StockLocationParams) {
  const init: RequestInit = {
    cache: 'force-cache',
    headers: {
      Authorization: `Bearer ${await getToken()}`
    },
    next: { tags: ['locations'], revalidate: 60 }
  };
  if (params) {
    const { sort = '', pageNumber, pageSize, search = '' } = params;
    const response = await fetch(
      `${localhost}/api/stock-locations?sort=${sort}&size=${pageSize}&page=${pageNumber}&name=${search}`,
      init
    );
    const responseData = await response.json();

    return responseData as ServerDTOArray<StockLocationsData>;
  } else {
    {
      const response = await fetch(`${localhost}/api/stock-locations`, init);
      const responseData = await response.json();

      return responseData as ServerDTOArray<StockLocationsData>;
    }
  }
}

export async function updateStockLocation(
  id: number,
  data: StockLocationsFormType
) {
  const response = await fetch(`${localhost}/api/stock-locations/${id}`, {
    method: 'PUT',
    headers: {
      Authorization: `Bearer ${await getToken()}`,
      'content-type': 'application/json'
    },
    body: JSON.stringify(data)
  });
  revalidateTag('locations');

  const responseData = await response.json();

  return responseData as ServerDTO<StockLocationsData>;
}

export async function deleteStockLocation(id: number) {
  const response = await fetch(`${localhost}/api/stock-locations/${id}`, {
    method: 'DELETE',
    headers: {
      Authorization: `Bearer ${await getToken()}`
    }
  });
  revalidateTag('locations');
  const responseData = await response.json();

  return responseData as ServerDTO<StockLocationsData>;
}
