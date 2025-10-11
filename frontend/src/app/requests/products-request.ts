'use server';

import { getCookie } from '@/lib/get-token';
import { ProductFormType, ProductsData } from '@/types/product-schema';
import { ServerDTO, ServerDTOArray } from '@/types/server-dto';
import { revalidateTag } from 'next/cache';

export async function createProduct(data: ProductFormType) {
  const response = await fetch('http://localhost:8080/api/products', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${await getCookie('accessToken')}`
    },
    body: JSON.stringify(data)
  });

  revalidateTag('products');

  const responseData = await response.json();

  return responseData as ServerDTO<ProductsData>;
}

export async function editProduct(data: ProductFormType, productId: number) {
  const response = await fetch(
    `http://localhost:8080/api/products/${productId}`,
    {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${await getCookie('accessToken')}`
      },
      body: JSON.stringify(data)
    }
  );

  revalidateTag('products');

  const responseData = await response.json();

  return responseData as ServerDTO<ProductsData>;
}

type Params = {
  pageSize?: number;
  pageNumber?: number;
  search?: string;
  sort?: string;
};

export async function getAllProducts({
  sort = '',
  pageSize,
  pageNumber,
  search = ''
}: Params) {
  const response = await fetch(
    `http://localhost:8080/api/products?sort=${sort}&size=${pageSize}&page=${pageNumber}&name=${search}`,
    {
      headers: {
        Authorization: `Bearer ${await getCookie('accessToken')}`
      },
      next: { tags: ['products'], revalidate: 60 }
    }
  );
  const responseData = await response.json();

  return responseData as ServerDTOArray<ProductsData>;
}

export async function softDeleteProduct(id: number) {
  const response = await fetch(`http://localhost:8080/api/products/${id}`, {
    method: 'DELETE',
    headers: {
      Authorization: `Bearer ${await getCookie('accessToken')}`
    }
  });

  revalidateTag('products');

  const responseData = await response.json();

  return responseData as ServerDTO<ProductsData>;
}
