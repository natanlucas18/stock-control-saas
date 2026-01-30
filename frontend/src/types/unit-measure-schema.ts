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
    acronym: string;
}

export const unitMeasureFormSchema = z.object({
    name: z.string().min(2, 'Insira um nome.'),
    acronym: z.string().min(1, 'Insira uma sigla.')
});

export type UnitMeasureFormType = z.infer<typeof unitMeasureFormSchema>;