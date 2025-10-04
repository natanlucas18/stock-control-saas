import z from 'zod';

export type ProductsData = {
  id: number;
  name: string;
  quantity: number;
  price: number;
  stockMax: number;
  stockMin: number;
  unitMeasure: string;
  stockLocation: {
    id: number;
    name: string;
  };
  stockStatus: {
    level: string;
    message: string;
  };
};

export const productFormSchema = z.object({
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
