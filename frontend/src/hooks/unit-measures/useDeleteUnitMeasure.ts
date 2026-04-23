import { softUnitMeasureDelete } from "@/services/unit-measure-service";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export function useDeleteUnitMeasure() {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (id: number) => softUnitMeasureDelete(id),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['unit-measures'] })
        }
    })
}