import { createReturnMovements } from "@/services/movements-service";
import { ReturnMovementsFormType } from "@/types/movements-schema";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export function useReturnMovements() {
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: (formData: ReturnMovementsFormType) => createReturnMovements(formData),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['movements-report'] })
        }
    })
}