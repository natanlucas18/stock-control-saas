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
  description: string,
  idSession: number,
  price: number,
  unitMeasure: string,
  quantity_min: number,
  quantity_max: number
};

export type ProductsData = {
  id: number,
  code: number,
  description: string,
  idSession: number,
  price: number,
  unitMeasure: string,
  quantity_min: number,
  quantity_max: number
}[];

