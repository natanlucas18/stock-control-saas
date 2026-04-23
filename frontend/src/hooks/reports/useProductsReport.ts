import { getReportProducts, ReportProductParams } from "@/services/report-product-service";
import { useQuery } from "@tanstack/react-query";

export function useProductsReport({pageNumber, pageSize, sort, status}: ReportProductParams) {
    return useQuery({
        queryKey: ['products-report', {pageNumber, pageSize, sort, status}],
        queryFn: () => getReportProducts({pageNumber, pageSize, sort, status})
    })
}