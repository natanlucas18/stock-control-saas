import { createUnitMeasure } from "@/services/unit-measure-service";
import { UnitMeasureFormType } from "@/types/unit-measure-schema";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export function useCreateUnitMeasure() {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (data: UnitMeasureFormType) => createUnitMeasure(data),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['unit-measures']})
        }
    })
}