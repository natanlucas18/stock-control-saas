'use client';

import { apiFetch } from '@/lib/api-client';
import { getApiUrl } from '@/lib/api-url';
import {
  EntryMovementsFormType,
  ExitMovementsFormType,
  MovementsData,
  ReturnMovementsFormType,
  TransferMovementsFormType
} from '@/types/movements-schema';
import { ServerDTO } from '@/types/server-dto';

const localhost = getApiUrl();

export async function createEntryMovements(
  formData: EntryMovementsFormType
){
  return apiFetch<ServerDTO<MovementsData>>(`${localhost}/api/movements/entry`, {
    method: 'POST',
    body: JSON.stringify(formData)
  })
}

export async function createExitMovements(
  formData: ExitMovementsFormType
){
  return apiFetch<ServerDTO<MovementsData>>(`${localhost}/api/movements/exit`, {
    method: 'POST',
    body: JSON.stringify(formData)
  })
}

export async function createTransferMovements(
  formData: TransferMovementsFormType
){
  return apiFetch<ServerDTO<MovementsData>>(`${localhost}/api/movements/transfer`, {
    method: 'POST',
    body: JSON.stringify(formData)
  })
}

export async function createReturnMovements(
  formData: ReturnMovementsFormType
){
  return apiFetch<ServerDTO<MovementsData>>(`${localhost}/api/movements/return`, {
    method: 'POST',
    body: JSON.stringify(formData)
  })
}
