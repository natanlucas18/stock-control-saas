'use client';

import { StockLocationsData, StockLocationParams } from '@/types/stock-location-schema'; 
import { PaginationOptions } from '@/types/server-dto';
import { useEffect, useState } from 'react';
import { getAllStockLocations } from '@/services/stock-location-service';

export function useStockLocation(params?: StockLocationParams) {
  const [stockLocations, setStockLocations] = useState<StockLocationsData[]>([]);
  const [paginationOptions, setPaginationOptions] =
    useState<PaginationOptions>();

  useEffect(() => {
    let isMounted = true;

    getAllStockLocations(params).then((response) => {
      if (isMounted) {
        setStockLocations(response.data.content);
        setPaginationOptions(response.data.pagination);
      }
    });

    return () => {
      isMounted = false;
    };
  }, [params]);

  return { stockLocations, paginationOptions };
}
