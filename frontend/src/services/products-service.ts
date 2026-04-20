'use client';

import { apiFetch } from '@/lib/api-client';
import { getApiUrl } from '@/lib/api-url';
import { Params } from '@/types/Params';
import {
  Product,
  ProductFormType,
  ProductMin
} from '@/types/product-schema';
import { ServerDTO, ServerDTOArray } from '@/types/server-dto';

const localhost = getApiUrl();

export async function createProduct(
  data: ProductFormType
){
  return apiFetch<ServerDTO<ProductMin>>(`${localhost}/api/products`, {
    method: 'POST',
    body: JSON.stringify(data)
  })
}

export async function editProduct(
  data: ProductFormType,
  productId: number
){
  return apiFetch<ServerDTO<ProductMin>>(`${localhost}/api/products/${productId}`, {
    method: 'PUT',
    body: JSON.stringify(data)
  })
}

export async function getAllProducts(
  {pageSize, pageNumber, search, sort}: Params
) {
  const safeSort = sort ?? 'code'
  const params = new URLSearchParams({
    size: String(pageSize),
    page: String(pageNumber),
    query: search ?? '',
    sort: safeSort
  })
  return apiFetch<ServerDTOArray<ProductMin>>(`${localhost}/api/products?${params.toString()}`, {
    method: 'GET',
  })
}

export async function getProductById(id: number) {
  return apiFetch<ServerDTO<Product>>(`${localhost}/api/products/${id}`, {
    method: 'GET',
  })
}

export async function softProductDelete(
  id: number
) {
  return apiFetch<ServerDTO<ProductMin>>(`${localhost}/api/products/${id}`, {
    method: 'DELETE'
  })
}
