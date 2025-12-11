import { Product } from './product-schema';
import { StockLocationsData } from './stock-location-schema';
import { User } from './user-schema';

export type MovimentsReportParams = {
  startDate?: string;
  endDate?: string;
  productId?: number;
  page?: number;
  size?: number;
  sort?: 'moment' | 'desc';
  type?: 'ENTRADA' | 'SAIDA';
};

export type MovimentsReport = {
  id: number;
  type: string;
  quantity: number;
  moment: string;
  note: string;
  product: Pick<Product, 'id' | 'code' | 'name' | 'unitMeasure'>;
  user: Pick<User, 'id' | 'name'>;
  fromStockLocation: StockLocationsData | null;
  toStockLocation: StockLocationsData | null;
};
