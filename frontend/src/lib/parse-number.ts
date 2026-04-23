export function parseNumber(value: string | null) {
  return value ? Number(value) : undefined;
}