import { z } from 'zod';

export const filterFormSchema = z
  .object({
    search: z.coerce.string<string>().optional(),
    type: z.coerce.string<string>().optional(),
    startDate: z.coerce.string<string>().optional(),
    endDate: z.coerce.string<string>().optional(),
    productId: z.coerce.string<string>().optional()
  })
  .superRefine((data, ctx) => {
    if (new Date(data.startDate!) > new Date(data.endDate!)) {
      ctx.addIssue({
        path: ['endDate'],
        message: 'A data inicial não pode ser maior que a final',
        code: 'custom'
      });
    }
  });

export type FilterFormType = z.infer<typeof filterFormSchema>;
