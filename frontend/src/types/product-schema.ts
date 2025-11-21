import z from 'zod';
import { StockLocationsData } from './stock-location-schema';

export type ProductParams = {
  pageSize?: number;
  pageNumber?: number;
  search?: string;
  sort?: string;
};

export type ProductMin = Pick<
  Product,
  'id' | 'code' | 'name' | 'totalQuantity' | 'unitMeasure' | 'stockStatus'
>;

export type Product = {
  id: number;
  code: string;
  name: string;
  price: number;
  totalQuantity: number;
  stockMax: number;
  stockMin: number;
  unitMeasure: string;
  stockStatus: string;
  stockLocations: StockLocationsData[];
};

export const productFormSchema = z.object({
  code: z.string().min(1, 'Insira um código do produto!'),
  name: z.string().min(1, 'Insira uma descrição para o produto!'),
  price: z.coerce.number<number>().min(1, 'Insira o preço do produto!'),
  stockMin: z.coerce
    .number<number>()
    .min(1, 'Insira uma quantidade minima para o produto!'),
  stockMax: z.coerce
    .number<number>()
    .min(1, 'Insira uma quantidade máxima para o produto!'),
  unitMeasure: z.string().min(1, 'Insira a unidade de medida do produto!')
});

export type ProductFormType = z.infer<typeof productFormSchema>;
