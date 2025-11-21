'use server';

import { getCookie } from '@/lib/get-token';
import {
  Product,
  ProductFormType,
  ProductMin,
  ProductParams
} from '@/types/product-schema';
import { ServerDTO, ServerDTOArray } from '@/types/server-dto';
import { revalidateTag } from 'next/cache';

export async function createProduct(
  data: ProductFormType
): Promise<ServerDTO<ProductMin>> {
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

  return responseData;
}

export async function editProduct(
  data: ProductFormType,
  productId: number
): Promise<ServerDTO<ProductMin>> {
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

  return responseData;
}

export async function getAllProducts(
  params?: ProductParams
): Promise<ServerDTOArray<ProductMin>> {
  const init: RequestInit = {
    cache: 'force-cache',
    headers: {
      Authorization: `Bearer ${await getCookie('accessToken')}`
    },
    next: { tags: ['products'], revalidate: 60 }
  };

  if (params) {
    const { sort = '', pageSize, pageNumber, search = '' } = params;
    const response = await fetch(
      `http://localhost:8080/api/products?sort=${sort}&size=${pageSize}&page=${pageNumber}&name=${search}`,
      init
    );
    const responseData = await response.json();

    return responseData;
  } else {
    {
      const response = await fetch(`http://localhost:8080/api/products`, init);
      const responseData = await response.json();

      return responseData;
    }
  }
}

export async function getProductById(id: number): Promise<ServerDTO<Product>> {
  const init: RequestInit = {
    cache: 'force-cache',
    headers: {
      Authorization: `Bearer ${await getCookie('accessToken')}`
    },
    next: { tags: ['products'], revalidate: 60 }
  };

  const response = await fetch(
    `http://localhost:8080/api/products/${id}`,
    init
  );
  const responseData = await response.json();

  return responseData;
}

export async function softProductDelete(
  id: number
): Promise<ServerDTO<ProductMin>> {
  const response = await fetch(`http://localhost:8080/api/products/${id}`, {
    method: 'DELETE',
    headers: {
      Authorization: `Bearer ${await getCookie('accessToken')}`
    }
  });

  revalidateTag('products');

  const responseData = await response.json();

  return responseData as ServerDTO<ProductMin>;
}
