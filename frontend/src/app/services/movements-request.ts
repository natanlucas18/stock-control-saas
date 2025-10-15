'use server';

import { getCookie } from '@/lib/get-token';
import { MovementsData, MovementsFormType } from '@/types/movements-schema';
import { ServerDTO } from '@/types/server-dto';
import { revalidateTag } from 'next/cache';

export async function createMovements(formData: MovementsFormType) {
  const response = await fetch('http://localhost:8080/api/movements', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${await getCookie('accessToken')}`
    },
    body: JSON.stringify(formData)
  });
  revalidateTag('movements');
  revalidateTag('products');
  const data = await response.json();

  return data as ServerDTO<MovementsData>;
}
