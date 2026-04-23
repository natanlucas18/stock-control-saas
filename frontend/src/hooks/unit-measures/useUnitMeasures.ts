import { getAllUnitMeasures } from "@/services/unit-measure-service";
import { UnitMeasureParams } from "@/types/unit-measure-schema";
import { useQuery } from "@tanstack/react-query";

export function useUnitMeasures({pageNumber, pageSize, search, sort}: UnitMeasureParams) {
    return useQuery({
        queryKey: ['unit-measures', {pageNumber, pageSize, search, sort}],
        queryFn: () => getAllUnitMeasures({pageNumber, pageSize, search, sort}),
    })
}