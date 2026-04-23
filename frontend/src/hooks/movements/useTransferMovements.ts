import { createTransferMovements } from "@/services/movements-service";
import { TransferMovementsFormType } from "@/types/movements-schema";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export function useTransferMovements() {
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: (formData: TransferMovementsFormType) => createTransferMovements(formData),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['movements-report', 'products', 'products-report']})
        }
    })
}