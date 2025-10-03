import type { NextRequest } from 'next/server';
import { NextResponse } from 'next/server';
import { getToken } from './lib/get-token';
import { PathLinks } from './types/path-links';

const protectedRoutes = [
  PathLinks.DASHBOARD,
  PathLinks.CREATE_PRODUCT,
  PathLinks.LIST_PRODUCTS,
  PathLinks.CREATE_STOCK_LOCATION,
  PathLinks.LIST_STOCK_LOCATIONS
];
// const reportRoutes = ['/relatorios'];
// const secret = process.env.NEXTAUTH_SECRET;

export async function middleware(req: NextRequest) {
  const { pathname } = req.nextUrl;
  console.log(pathname);

  const isProtected = protectedRoutes.some((route) =>
    pathname.startsWith(route)
  );
  if (!isProtected) return NextResponse.next();

  const token = await getToken();

  if (!token) {
    const loginUrl = new URL(PathLinks.LOGIN, req.url);
    loginUrl.searchParams.set('callbackUrl', req.nextUrl.pathname);
    return NextResponse.redirect(loginUrl);
  }

  // if (reportRoutes.includes(pathname) && token.role !== 'admin') {
  //   return NextResponse.redirect(new URL('/home', req.url));
  // }

  return NextResponse.next();
}

export const config = {
  matcher: [
    `${PathLinks.DASHBOARD}/:path*`,
    `${PathLinks.CREATE_PRODUCT}/:path*`,
    `${PathLinks.LIST_PRODUCTS}/:path*`,
    `${PathLinks.CREATE_STOCK_LOCATION}/:path*`,
    `${PathLinks.LIST_STOCK_LOCATIONS}/:path*`
  ]
};
