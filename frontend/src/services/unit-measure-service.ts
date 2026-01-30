'use server';
import { getApiUrl } from '@/lib/api-url';
import { getCookie } from '@/lib/get-token';
import { ServerDTO } from '@/types/server-dto';
import {
  UnitMeasureData,
  UnitMeasureFormType,
  UnitMeasureParams
} from '@/types/unit-measure-schema';
import { revalidateTag } from 'next/cache';

const localhost = getApiUrl();

export async function createUnitMeasure(
  data: UnitMeasureFormType
): Promise<ServerDTO<UnitMeasureData>> {
  const response = await fetch(`${localhost}/api/unit-measures`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${await getCookie('accessToken')}`
    },
    body: JSON.stringify(data)
  });

  revalidateTag('unit-measure');

  const responseData = await response.json();

  return responseData;
}

export async function getAllUnitMeasure(params?: UnitMeasureParams) {
  const init: RequestInit = {
    cache: 'force-cache',
    headers: {
      Authorization: `Bearer ${await getCookie('accessToken')}`
    },
    next: { tags: ['unit-measure'], revalidate: 60 }
  };

  if (params) {
    const { sort = '', pageNumber, pageSize, search = '' } = params;
    const response = await fetch(
      `${localhost}/api/unit-measures?sort=${sort}&page=${pageNumber}&size=${pageSize}&search=${search}`,
      init
    );
    const responseData = await response.json();
    return responseData;
  } else {
    const response = await fetch(`${localhost}/api/unit-measures`, init);
    const responseData = await response.json();
  }
}

export async function getUnitMeasureById(id: number) {
  const init: RequestInit = {
    cache: 'force-cache',
    headers: {
      Authorization: `Bearer ${await getCookie('accessToken')}`
    }
  };

  const response = await fetch(`${localhost}/api/unit-measures/${id}`, init);
  const responseData = await response.json();

  return responseData;
}

export async function editUnitMeasure(
  data: UnitMeasureFormType,
  unitMeasureId: number
): Promise<ServerDTO<UnitMeasureData>> {
  const response = await fetch(
    `${localhost}/api/unit-measures/${unitMeasureId}`,
    {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${await getCookie('accessToken')}`
      },
      body: JSON.stringify(data)
    }
  );

  revalidateTag('unit-measure');

  const responseData = await response.json();

  return responseData;
}

export async function softUnitMeasureDelete(
  id: number
): Promise<ServerDTO<UnitMeasureData>> {
  const response = await fetch(`${localhost}/api/unit-measures/${id}`, {
    method: 'DELETE',
    headers: {
      Authorization: `Bearer ${await getCookie('accessToken')}`
    }
  });

  revalidateTag('unit-measure');

  const responseData = await response.json();

  return responseData;
}
