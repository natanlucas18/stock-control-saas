'use client';

import { ProductParams, ProductsData } from '@/types/product-schema';
import { PaginationOptions } from '@/types/server-dto';
import { useEffect, useState } from 'react';
import { getAllProducts } from '../services/products-service';

export function useProducts(params?: ProductParams) {
  const [products, setProducts] = useState<ProductsData[]>([]);
  const [paginationOptions, setPaginationOptions] =
    useState<PaginationOptions>();

  useEffect(() => {
    let isMounted = true;

    getAllProducts(params).then((response) => {
      if (isMounted) {
        setProducts(response.data.content);
        setPaginationOptions(response.data.pagination);
      }
    });

    return () => {
      isMounted = false;
    };
  }, [params]);

  return { products, paginationOptions };
}
