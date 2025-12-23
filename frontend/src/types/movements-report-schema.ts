import { Product } from './product-schema';
import { StockLocationsData } from './stock-location-schema';
import { User } from './user-schema';

export type MovementsReportParams = {
  search: string;
  startDate?: string;
  endDate?: string;
  productId?: number;
  page?: number;
  size?: number;
  sort?: 'moment' | 'desc';
  type?: 'ENTRADA' | 'SAIDA' | 'DEVOLUCAO' | 'TRANSFERENCIA';
};

export type MovementsReport = {
  id: number;
  type: string;
  quantity: number;
  unitMeasure: string;
  note: string;
  moment: string;
  product: Pick<Product, 'id' | 'code' | 'name' | 'unitMeasure'>;
  user: Pick<User, 'id' | 'name'>;
  fromStockLocation: StockLocationsData | null;
  toStockLocation: StockLocationsData | null;
};
