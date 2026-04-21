import { createEntryMovements } from "@/services/movements-service";
import { EntryMovementsFormType } from "@/types/movements-schema";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export function useCreateMovements() {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (formData: EntryMovementsFormType) => createEntryMovements(formData),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['movements-report']})
        }
    })
}