import z from 'zod';
import { StockLocationsData } from './stock-location-schema';

export type ProductParams = {
  pageSize?: number;
  pageNumber?: number;
  search?: string;
  sort?: string;
};

export type ProductsData = {
  id: number;
  code: string;
  name: string;
  quantity: number;
  price: number;
  stockMax: number;
  stockMin: number;
  unitMeasure: string;
  stockLocation: StockLocationsData;
  stockStatus: {
    level: string;
    message: string;
  };
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
  unitMeasure: z.string().min(1, 'Insira a unidade de medida do produto!'),
  stockLocationId: z.coerce
    .number<number>()
    .min(1, 'Insira o local de estoque do produto!')
});

export type ProductFormType = z.infer<typeof productFormSchema>;
