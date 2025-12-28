import z from "zod";

export type UnitMeasureParams = {
    search?: string;
    pageSize?: number;
    pageNumber?: number;
    sort?: string;
};

export type UnitMeasureData = {
    id: number;
    name: string;
}

export const unitMeasureFormSchema = z.object({
    name: z.string().min(2, 'Insira um nome.')
});

export type UnitMeasureFormType = z.infer<typeof unitMeasureFormSchema>;