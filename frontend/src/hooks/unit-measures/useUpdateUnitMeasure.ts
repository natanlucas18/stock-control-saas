import { editUnitMeasure } from "@/services/unit-measure-service";
import { UnitMeasureFormType } from "@/types/unit-measure-schema"
import { useMutation, useQueryClient } from "@tanstack/react-query"

export type UpdateUnitMeasurePayload = {
    id: number,
    data: UnitMeasureFormType
}

export function useUpdateUnitMeasure() {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: ({data, id}: UpdateUnitMeasurePayload) => editUnitMeasure(data, id),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['unit-measures'] })
        }
    })
}