import { Product } from './product-schema';
import { StockLocationsData } from './stock-location-schema';
import { AuthResponse } from './auth-response';

export type MovementsReportParams = {
  search: string | undefined;
  startDate?: string | undefined;
  endDate?: string | undefined;
  productId?: number | undefined;
  page?: number | undefined;
  size?: number | undefined;
  sort?: string | undefined;
  type?: string | undefined;
};


export type MovementsReport = {
  id: number;
  type: string;
  quantity: number;
  unitMeasure: string;
  note: string;
  moment: string;
  product: Pick<Product, 'id' | 'code' | 'name' | 'unitMeasure'>;
  user: Pick<AuthResponse, 'userId' | 'userName'>;
  fromStockLocation: StockLocationsData | null;
  toStockLocation: StockLocationsData | null;
};
