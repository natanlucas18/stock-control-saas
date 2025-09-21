export type AuthData = {
  accessToken: string
};

export type FormLogin = {
  username: string;
  password: string;
};

export type FormDataMovements = {
  description: string,
  quantity: number
};

export type FormProducts = {
  name: string,
  stockLocationId: number,
  price: number,
  unitMeasure: string,
  stockMin: number,
  stockMax: number
};

export type ProductsData = {
  id: number,
  name: string,
  stockLocationId: number,
  price: number,
  unitMeasure: string,
  stockMin: number,
  stockMax: number
}[];

export type FormStockLocation = {
  name: string;
}

export type StockLocationData = {
  id: number;
  name: string;
};

