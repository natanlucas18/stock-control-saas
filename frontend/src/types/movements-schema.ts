import z from 'zod';
import { Product } from './product-schema';
import { StockLocationsData } from './stock-location-schema';
import { User } from './user-schema';

export type MovementsData = {
  id: number;
  type: string;
  quantity: number;
  moment: string;
  note: string;
  product: Product;
  user: Pick<User, 'userId' | 'userName'>;
  stockLocation: StockLocationsData;
};

export const entryMovementsFormSchema = z.object({
  type: z.enum(['ENTRADA']),
  quantity: z.coerce.number<number>().min(1, 'Insira uma quantidade!'),
  note: z.string().min(1, 'Insira uma nota!'),
  productId: z.coerce.number<number>().min(1, 'Insira um produto!'),
  toStockLocationId: z.coerce
    .number<number>()
    .min(1, 'Insira um local de estoque!')
});

export type EntryMovementsFormType = z.infer<typeof entryMovementsFormSchema>;

export const exitMovementsFormSchema = z.object({
  type: z.enum(['SAIDA']),
  quantity: z.coerce.number<number>().min(1, 'Insira uma quantidade!'),
  note: z.string().min(1, 'Insira uma nota!'),
  productId: z.coerce.number<number>().min(1, 'Insira um produto!'),
  fromStockLocationId: z.coerce
    .number<number>()
    .min(1, 'Insira um local de estoque!')
});

export type ExitMovementsFormType = z.infer<typeof exitMovementsFormSchema>;

export const transferMovementsFormSchema = z.object({
  type: z.enum(['TRANSFERENCIA']),
  quantity: z.coerce.number<number>().min(1, 'Insira uma quantidade!'),
  note: z.string().min(1, 'Insira uma nota!'),
  productId: z.coerce.number<number>().min(1, 'Insira um produto!'),
  fromStockLocationId: z.coerce
    .number<number>()
    .min(1, 'Insira um local de estoque!'),
  toStockLocationId: z.coerce
    .number<number>()
    .min(1, 'Insira um local de estoque!')
});

export type TransferMovementsFormType = z.infer<
  typeof transferMovementsFormSchema
>;

export const returnMovementsFormSchema = z.object({
  type: z.enum(['DEVOLUCAO']),
  quantity: z.coerce.number<number>().min(1, 'Insira uma quantidade!'),
  note: z.string().min(1, 'Insira uma nota!'),
  productId: z.coerce.number<number>().min(1, 'Insira um produto!'),
  toStockLocationId: z.coerce
    .number<number>()
    .min(1, 'Insira um local de estoque!')
});

export type ReturnMovementsFormType = z.infer<typeof returnMovementsFormSchema>;
