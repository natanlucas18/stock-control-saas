'use server';

import { getApiUrl } from '@/lib/api-url';
import { getCookie } from '@/lib/get-token';
import {
  EntryMovementsFormType,
  ExitMovementsFormType,
  MovementsData,
  ReturnMovementsFormType,
  TransferMovementsFormType
} from '@/types/movements-schema';
import { ServerDTO } from '@/types/server-dto';
import { revalidateTag } from 'next/cache';

const localhost = getApiUrl();

export async function createEntryMovements(
  formData: EntryMovementsFormType
): Promise<ServerDTO<MovementsData>> {
  const response = await fetch(`${localhost}/api/movements/entry`, {
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

  return data;
}

export async function createExitMovements(
  formData: ExitMovementsFormType
): Promise<ServerDTO<MovementsData>> {
  const response = await fetch(`${localhost}/api/movements/exit`, {
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

  return data;
}

export async function createTransferMovements(
  formData: TransferMovementsFormType
): Promise<ServerDTO<MovementsData>> {
  const response = await fetch(`${localhost}/api/movements/transfer`, {
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

  return data;
}

export async function createReturnMovements(
  formData: ReturnMovementsFormType
): Promise<ServerDTO<MovementsData>> {
  const response = await fetch(`${localhost}/api/movements/return`, {
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

  return data;
}
