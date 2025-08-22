export type AuthData = {
  id: number,
  email: string,
  name: string,
  token: string
};

export type FormLogin = {
  email: string;
  password: string;
};

export type FormDataMovements = {
  description: string,
  quantity: number
};

export type FormProducts = {
  description: string,
  idSession: number,
  quantity_min: number,
  quantity_max: number
};

export type ProductsData = {
  id: number,
  description: string,
  idSession: number,
  quantity_min: number,
  quantity_max: number
}[];

