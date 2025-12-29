export function getApiUrl() {
  // Se estivermos no navegador, usamos a URL pública
  if (typeof window !== 'undefined') {
    return process.env.NEXT_PUBLIC_API_URL_CLIENT;
  }

  // Se estivermos no servidor (Docker), usamos a URL interna
  return process.env.INTERNAL_API_URL;
}
