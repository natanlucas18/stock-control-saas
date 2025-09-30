export type AuthData = {
  user_id: string;
  user_name: string;
  user_roles: [];
  access_token: string;
  created_at: string;
  expires_at: string;
};

export type LoginForm = {
  username: string;
  password: string;
};

export type FormLogin = {
  username: string;
  password: string;
};

export type FormDataMovements = {
  description: string;
  quantity: number;
};

export type FormProducts = {
  description: string;
  idSession: number;
  price: number;
  unitMeasure: string;
  quantity_min: number;
  quantity_max: number;
};

export type ProductsData = {
  id: number;
  code: number;
  description: string;
  idSession: number;
  price: number;
  unitMeasure: string;
  quantity_min: number;
  quantity_max: number;
}[];
