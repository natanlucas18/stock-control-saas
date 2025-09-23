'use server';

import { ServerDTO } from '@/types/server-dto';
import { StockLocationsData, StockLocationsFormType } from '@/types/stock-location-schema';
import { revalidateTag } from 'next/cache';
import { cookies } from 'next/headers';

const token = (await cookies()).get('AccessToken')?.value || '';

export async function createStockLocations(data: StockLocationsFormType) {
  const response = await fetch('http://localhost:8080/api/stock-locations', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify(data)
  });

  revalidateTag('products');

  const responseData = await response.json();

  return responseData as ServerDTO<StockLocationsData>;
}

export async function getAllStockLocations() {
  const response = await fetch(`http://localhost:8080/api/products`, {
    headers: {
      'Authorization': `Bearer ${token}`
    },
    next: { tags: ['products'], revalidate: 60 }
  });
  const responseData = await response.json();

  return responseData as ServerDTO<StockLocationsData[]>;
}
