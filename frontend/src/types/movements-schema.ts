import z from 'zod';
import { ProductsData } from './product-schema';
import { StockLocationsData } from './stock-location-schema';
import { User } from './user-schema';

export type MovementsData = {
  id: number;
  type: string;
  quantity: number;
  moment: string;
  note: string;
  product: ProductsData;
  user: Pick<User, 'userId' | 'userName'>;
  stockLocation: StockLocationsData;
};

export const movementsFormSchema = z.object({
  type: z.enum(['ENTRADA', 'SAIDA']),
  quantity: z.coerce.number<number>().min(1, 'Insira uma quantidade!'),
  note: z.string().min(1, 'Insira uma nota!'),
  productId: z.coerce.number<number>().min(1, 'Insira um produto!'),
  stockLocationId: z.coerce
    .number<number>()
    .min(1, 'Insira um local de estoque!')
});

export type MovementsFormType = z.infer<typeof movementsFormSchema>;
