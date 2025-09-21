export type ServerDTO<T = null> = {
  data: T;
  error: {
    timestamp: string;
    status: number;
    message: string;
    path: string;
  } | null;
  success: boolean;
};
