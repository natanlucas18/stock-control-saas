import { createExitMovements } from "@/services/movements-service";
import { ExitMovementsFormType } from "@/types/movements-schema";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export function useExitMovements() {
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: (formData: ExitMovementsFormType) => createExitMovements(formData),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['movements-report', 'products', 'products-report']})
        }
    })
}