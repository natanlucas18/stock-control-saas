import { movementsReportFilterService } from "@/services/movements-report-service";
import { MovementsReportParams } from "@/types/movements-report-schema";
import { useQuery } from "@tanstack/react-query";

export function useMovementsReport({
    startDate, endDate, productId, page, search, size, sort, type
}: MovementsReportParams) {
    return useQuery({
        queryKey: ['movements-report', {
            startDate, endDate, productId, page, search, size, sort, type
        }],
        queryFn: () => movementsReportFilterService(
            {startDate, endDate, productId, page, search, size, sort, type
        }),
    })
}