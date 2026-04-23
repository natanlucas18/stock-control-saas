import { editProduct } from "@/services/products-service";
import { ProductFormType } from "@/types/product-schema";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export type UpdateProductPayload = {
    id: number;
    data: ProductFormType
}
export function useUpdateProduct() {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: ({id, data}: UpdateProductPayload) => editProduct(data, id),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['products','products-report'] })
        }
    })
}