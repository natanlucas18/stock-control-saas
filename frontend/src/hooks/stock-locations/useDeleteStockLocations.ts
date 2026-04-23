import { deleteStockLocation } from "@/services/stock-location-service";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export function useDeleteStockLocation() {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (id: number) => deleteStockLocation(id),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['locations'] })
        }
    })
}