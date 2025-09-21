'use server';

import { ProductFormType, ProductsData } from '@/types/product-schema';
import { ServerDTO } from '@/types/server-dto';
import { revalidateTag } from 'next/cache';
import { cookies } from 'next/headers';

const token = (await cookies()).get('AccessToken')?.value || '';

export async function createProduct(data: ProductFormType) {
  const response = await fetch('http://localhost:3000/api/products', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`
    },
    body: JSON.stringify(data)
  });

  revalidateTag('products');

  const responseData = await response.json();

  return responseData as ServerDTO<ProductsData>;
}

export async function getAllProducts() {
  const response = await fetch(`http://localhost:8080/api/products`, {
    next: { tags: ['products'], revalidate: 60 }
  });
  const responseData = await response.json();

  return responseData as ServerDTO<ProductsData[]>;
}
