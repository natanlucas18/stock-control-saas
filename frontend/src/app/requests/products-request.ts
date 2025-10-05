'use server';

import { getToken } from '@/lib/get-token';
import { ProductFormType, ProductsData } from '@/types/product-schema';
import { ServerDTO, ServerDTOArray } from '@/types/server-dto';
import { revalidateTag } from 'next/cache';

export async function createProduct(data: ProductFormType) {
  const response = await fetch('http://localhost:8080/api/products', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${await getToken()}`
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
        Authorization: `Bearer ${await getToken()}`
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
};

export async function getAllProducts({ pageSize, pageNumber }: Params) {
  const response = await fetch(
    `http://localhost:8080/api/products?size=${pageSize}&page=${pageNumber}`,
    {
      headers: {
        Authorization: `Bearer ${await getToken()}`
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
      Authorization: `Bearer ${await getToken()}`
    }
  });

  revalidateTag('products');

  const responseData = await response.json();

  return responseData as ServerDTO<ProductsData>;
}
