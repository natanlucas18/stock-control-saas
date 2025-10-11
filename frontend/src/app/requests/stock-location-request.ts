'use server';

import { getCookie } from '@/lib/get-token';
import { ServerDTO, ServerDTOArray } from '@/types/server-dto';
import {
  StockLocationsData,
  StockLocationsFormType
} from '@/types/stock-location-schema';
import { revalidateTag } from 'next/cache';
export async function createStockLocations(data: StockLocationsFormType) {
  const response = await fetch('http://localhost:8080/api/stock-locations', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${await getCookie('accessToken')}`
    },
    body: JSON.stringify(data)
  });

  revalidateTag('locations');

  const responseData = await response.json();

  return responseData as ServerDTO<StockLocationsData>;
}

type Params = {
  pageSize?: string;
  pageNumber?: string;
};

export async function getAllStockLocations({ pageSize, pageNumber }: Params) {
  const response = await fetch(
    `http://localhost:8080/api/stock-locations?size=${pageSize}&page=${pageNumber}`,
    {
      headers: {
        Authorization: `Bearer ${await getCookie('accessToken')}`
      },
      next: { tags: ['locations'], revalidate: 60 }
    }
  );
  const responseData = await response.json();

  return responseData as ServerDTOArray<StockLocationsData>;
}

export async function updateStockLocation(
  id: number,
  data: StockLocationsFormType
) {
  const response = await fetch(
    `http://localhost:8080/api/stock-locations/${id}`,
    {
      method: 'PUT',
      headers: {
        Authorization: `Bearer ${await getCookie('accessToken')}`,
        'content-type': 'application/json'
      },
      body: JSON.stringify(data)
    }
  );
  revalidateTag('locations');

  const responseData = await response.json();

  return responseData as ServerDTO<StockLocationsData>;
}

export async function deleteStockLocation(id: number) {
  const response = await fetch(
    `http://localhost:8080/api/stock-locations/${id}`,
    {
      method: 'DELETE',
      headers: {
        Authorization: `Bearer ${await getCookie('accessToken')}`
      }
    }
  );
  revalidateTag('locations');
  const responseData = await response.json();

  return responseData as ServerDTO<StockLocationsData>;
}
