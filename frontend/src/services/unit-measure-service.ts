'use client';
import { apiFetch } from '@/lib/api-client';
import { getApiUrl } from '@/lib/api-url';
import { createQueryParams } from '@/lib/create-query-params';
import { ServerDTO, ServerDTOArray } from '@/types/server-dto';
import {
  UnitMeasureData,
  UnitMeasureFormType,
  UnitMeasureParams
} from '@/types/unit-measure-schema';

const localhost = getApiUrl();

export async function createUnitMeasure(
  data: UnitMeasureFormType
) {
  return apiFetch<ServerDTO<UnitMeasureData>>(
    `${localhost}/api/unit-measures`, {
    method: 'POST',
    body: JSON.stringify(data)
  })
}

export async function getAllUnitMeasures(
  {pageNumber, pageSize, search, sort}: UnitMeasureParams
) {
  const params = createQueryParams({
    page: pageNumber,
    size: pageSize,
    query: search,
    sort,
  })
  return apiFetch<ServerDTOArray<UnitMeasureData>>(
    `${localhost}/api/unit-measures?${params.toString()}`
  )
}

export async function getUnitMeasureById(id: number) {
  return apiFetch<ServerDTO<UnitMeasureData>>(
    `${localhost}/api/unit-measures/${id}`, {
      method: 'GET',
    }
  )
}

export async function editUnitMeasure(
  data: UnitMeasureFormType,
  id: number
) {
  return apiFetch<ServerDTO<UnitMeasureData>>(
    `${localhost}/api/unit-measures/${id}`, {
      method: 'PUT',
      body: JSON.stringify(data)
    }
  )
}

export async function softUnitMeasureDelete(
  id: number
){
  return apiFetch<ServerDTO<UnitMeasureData>>(
    `${localhost}/api/unit-measures/${id}`, {
      method: 'DELETE',
    }
  )
}
