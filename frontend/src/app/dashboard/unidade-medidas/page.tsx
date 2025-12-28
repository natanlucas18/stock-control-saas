import { getAllUnitMeasure } from "@/services/unit-measure-service";
import { UnitMeasureParams } from "@/types/unit-measure-schema";
import { UnitMeasureTable } from "./components/unit-measure-table";

export default async function UnitMeasureListPage (searchParams: UnitMeasureParams) {
    const { data } = await getAllUnitMeasure({
        pageSize: searchParams?.pageSize,
        pageNumber: searchParams?.pageNumber,
        sort: searchParams?.sort,
        search: searchParams?.search,
    })

    const unitMeasure = data?.content ?? [];
    const pagination = data?.pagination ?? [];


    return (
        <UnitMeasureTable
          unitsMeasure={unitMeasure}
          paginationOptions={pagination}
        />
    )
}