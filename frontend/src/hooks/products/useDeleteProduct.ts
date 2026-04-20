import { softProductDelete } from "@/services/products-service";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export function useDeleteProduct() {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (id:number) => softProductDelete(id),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['products'] })
        }
    })
}