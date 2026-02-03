'use server';

import { getApiUrl } from '@/lib/api-url';
import { getToken } from '@/lib/get-token';
import { MovementsData, MovementsFormType } from '@/types/movements-schema';
import { ServerDTO } from '@/types/server-dto';
import { revalidateTag } from 'next/cache';

const localhost = getApiUrl();

export async function createMovements(
  formData: MovementsFormType
): Promise<ServerDTO<MovementsData>> {
  const response = await fetch(`${localhost}/api/movements`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${await getToken()}`
    },
    body: JSON.stringify(formData)
  });
  revalidateTag('movements');
  revalidateTag('products');

  const data = await response.json();

  return data;
}
