import { createStockLocation } from "@/services/stock-location-service";
import { StockLocationsFormType } from "@/types/stock-location-schema";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export function useCreateStockLocation() {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (data: StockLocationsFormType) => createStockLocation(data),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['locations']})
        }
    })
}