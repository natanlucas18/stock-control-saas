import { clsx, type ClassValue } from 'clsx';
import { twMerge } from 'tailwind-merge';

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

export function getVisiblePages(current: number, total: number, delta = 2) {
  const pages: (number | string)[] = [];

  // Sempre mostra a primeira
  pages.push(1);

  // Intervalo do meio
  const start = Math.max(2, current - delta);
  const end = Math.min(total - 1, current + delta);

  if (start > 2) {
    pages.push('ellipsis-left');
  }

  for (let i = start; i <= end; i++) {
    pages.push(i);
  }

  if (end < total - 1) {
    pages.push('ellipsis-right');
  }

  // Sempre mostra a última
  if (total > 1) {
    pages.push(total);
  }

  return pages;
}

type Primitive = string | number | boolean | Date;
type FormDataValue = Primitive | File | Blob | null | undefined;

export function objectToFormData<T extends Record<string, FormDataValue>>(
  object: T
) {
  const formData = new FormData();

  Object.entries(object).forEach(([key, value]) => {
    if (value) {
      formData.append(key, String(value));
    }
  });

  return formData;
}
