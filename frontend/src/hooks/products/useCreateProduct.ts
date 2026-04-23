import { createProduct } from "@/services/products-service";
import { ProductFormType } from "@/types/product-schema";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export function useCreateProduct() {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (data: ProductFormType) => createProduct(data),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['products','products-report']})
        }
    })
}