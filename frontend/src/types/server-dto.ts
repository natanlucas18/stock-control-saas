export type PaginationOptions = {
  pageNumber: number;
  pageSize: number;
  totalElements: number;
  totalPages: number;
  last: boolean;
  first: boolean;
};

type error = {
  timestamp: string;
  status: number;
  message: string;
  path: string;
};

export type ServerDTO<T = null> = {
  data: T;
  errors: error[] | null;
  success: boolean;
};

export type ServerDTOArray<T = null> = {
  data: { content: T[]; pagination: PaginationOptions };
  errors: error[] | null;
  success: boolean;
};
