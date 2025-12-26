import * as z from 'zod'
export const reportProductsFormFiltersSchema = z.object({
    status: z.enum(['LOW', 'NORMAL', 'HIGH']),
})

export type ReportProductsFormFiltersType = z.infer<typeof reportProductsFormFiltersSchema>