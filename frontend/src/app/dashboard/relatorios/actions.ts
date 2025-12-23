'use server';

import { filterFormSchema, FilterFormType } from '@/types/filter-schema';
import z from 'zod';

type FieldErrorsType = {
  search?: string[] | undefined;
  startDate?: string[] | undefined;
  endDate?: string[] | undefined;
  productId?: string[] | undefined;
  type?: string[] | undefined;
};

type ValidationFieldsDTO = {
  data?: FilterFormType;
  success?: boolean;
  errors?: FieldErrorsType;
};

export async function filterFormValidationAction(
  prevState: ValidationFieldsDTO,
  formdata: FormData
): Promise<ValidationFieldsDTO> {
  const rawData = {
    search: formdata.get('search'),
    startDate: formdata.get('startDate'),
    endDate: formdata.get('endDate'),
    productId: formdata.get('productId'),
    type: formdata.get('type')
  };
  const parsedData = filterFormSchema.safeParse(rawData);

  if (!parsedData.success) {
    return {
      success: parsedData.success,
      data: parsedData.data,
      errors: z.flattenError(parsedData.error).fieldErrors
    };
  }

  return {
    success: parsedData.success,
    data: parsedData.data,
    errors: undefined
  };
}
