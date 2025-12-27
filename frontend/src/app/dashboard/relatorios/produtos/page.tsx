import { getReportProducts } from "@/services/report-product-service";
import { ReportsProductsTable } from "./components/report-products-table";

type UrlParams = {
  searchParams: Promise<{
    sort?: string;
    size?: number;
    page?: number;
    status?: string;
  }>;
};

export default async function ReportProductsListPage({ searchParams }: UrlParams) {
  const params = await searchParams;
  const { data } = await getReportProducts({
    pageSize: params?.size,
    pageNumber: params?.page,
    sort: params?.sort,
    status: params?.status
  });
  const products = data?.content ?? [];
  const pagination = data?.pagination ?? [];

  return (
    <ReportsProductsTable
      products={products}
      paginationOptions={pagination}
    />
  );
}
