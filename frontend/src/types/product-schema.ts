import z from 'zod';

export type ProductsData = {
  id: number;
  name: string;
  price: number;
  stockMin: number;
  stockMax: number;
  unitMeasure: string;
  stockLocationId: number;
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
