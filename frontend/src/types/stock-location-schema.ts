import z from 'zod';
export type StockLocationsData = {
  id: number,
  name: string,
};


export const stockLocationsFormSchema = z.object({
  name: z.string().min(1, 'Insira um nome para o local de estoque!'),
})

export type StockLocationsFormType = z.infer<typeof stockLocationsFormSchema>;

