import { getAllStockLocations } from "@/services/stock-location-service";
import { Params } from "@/types/Params";
import { useQuery } from "@tanstack/react-query";

export function useStockLocations({ pageSize, pageNumber, search, sort }: Params) {
    return useQuery({
        queryKey:['locations', { pageSize, pageNumber, search, sort }],
        queryFn: () => getAllStockLocations({ pageSize, pageNumber, search, sort }),
    })
}