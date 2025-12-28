'use client';

import { PaginationOptions } from '@/types/server-dto';
import { useEffect, useState } from 'react';
import { UnitMeasureData, UnitMeasureParams } from '@/types/unit-measure-schema';
import { getAllUnitMeasure } from '@/services/unit-measure-service';

export function useUnitMeasure(params?: UnitMeasureParams) {
  const [unitsMeasure, setUnitsMeasure] = useState<UnitMeasureData[]>([]);
  const [paginationOptions, setPaginationOptions] =
    useState<PaginationOptions>();

  useEffect(() => {
    let isMounted = true;

    getAllUnitMeasure(params).then((response) => {
      if (isMounted) {
        setUnitsMeasure(response.data.content);
        setPaginationOptions(response.data.pagination);
      }
    });

    return () => {
      isMounted = false;
    };
  }, [params]);

  return { unitsMeasure, paginationOptions };
}
