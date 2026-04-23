import { getAllProducts } from "@/services/products-service";
import { Params } from "@/types/Params";
import { useQuery } from "@tanstack/react-query";

export function useProducts({pageSize, pageNumber, search, sort}: Params) {
    return useQuery({
        queryKey: ['products', {pageSize, pageNumber, search, sort}],
        queryFn: () => getAllProducts({pageSize, pageNumber, search, sort})
    })
}