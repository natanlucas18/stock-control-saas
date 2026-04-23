import { updateStockLocation } from "@/services/stock-location-service";
import { StockLocationsFormType } from "@/types/stock-location-schema";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export type UpdateStockLocationPayload = {
    id: number;
    data: StockLocationsFormType;
}

export function useUpdateStockLocation() {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: ({id, data}: UpdateStockLocationPayload) => updateStockLocation(id, data),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['locations'] })
        }
    })
}