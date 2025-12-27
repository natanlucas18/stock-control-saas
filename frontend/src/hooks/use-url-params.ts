import { usePathname, useRouter, useSearchParams } from 'next/navigation';

export function useUrlParams() {
  const searchParams = useSearchParams();
  const pathname = usePathname();
  const { replace } = useRouter();
  const params = new URLSearchParams(searchParams);

  function setUrlParam(key: string, value: string | undefined | null) {
    if (value !== 'null' && value !== undefined && value !== null) {
      params.set(key, value);
    } else {
      params.delete(key);
    }
    replace(`${pathname}?${params.toString()}`, { scroll: false });
  }

  return { setUrlParam, params };
}
