import type { NextRequest } from 'next/server';
import { NextResponse } from 'next/server';
import { getCookie } from './lib/get-token';
import { PathLinks } from './types/path-links';

const protectedRoutes = [
  PathLinks.DASHBOARD,
  PathLinks.CREATE_PRODUCT,
  PathLinks.LIST_PRODUCTS,
  PathLinks.CREATE_STOCK_LOCATION,
  PathLinks.LIST_STOCK_LOCATIONS,
  PathLinks.MOVEMENTS,
  PathLinks.REPORTS,
  PathLinks.REGISTER
];

// const secret = process.env.NEXTAUTH_SECRET;

export async function middleware(req: NextRequest) {
  const { pathname } = req.nextUrl;
  const isProtected = protectedRoutes.some((route) =>
    pathname.startsWith(route)
  );

  if (!isProtected) return NextResponse.next();

  const regex = /[\[\"\]]/g;
  const token = await getCookie('accessToken');
  const userRoles = (await getCookie('userRoles'))
    .replace(regex, '')
    .split(',');

  if (!token) {
    const loginUrl = new URL(PathLinks.LOGIN, req.url);
    loginUrl.searchParams.set('callbackUrl', req.nextUrl.pathname);
    return NextResponse.redirect(loginUrl);
  }

  const reportsRouter = ['/relatorios', '/resgister'];
  const isAdmin = userRoles.includes('ROLE_ADMIN');
  const isDev = userRoles.includes('ROLE_DEV');

  if (
    reportsRouter.includes(pathname) &&
    pathname === '/relatorios' &&
    !isAdmin
  ) {
    return NextResponse.redirect(new URL('/home', req.url));
  } else if (
    reportsRouter.includes(pathname) &&
    pathname === '/resgister' &&
    !isDev
  ) {
    return NextResponse.redirect(new URL('/home', req.url));
  }

  return NextResponse.next();
}

export const config = {
  matcher: [
    `${PathLinks.DASHBOARD}/:path*`,
    `${PathLinks.CREATE_PRODUCT}/:path*`,
    `${PathLinks.LIST_PRODUCTS}/:path*`,
    `${PathLinks.CREATE_STOCK_LOCATION}/:path*`,
    `${PathLinks.LIST_STOCK_LOCATIONS}/:path*`,
    `${PathLinks.MOVEMENTS}/:path*`,
    `${PathLinks.REPORTS}/:path*`,
    `${PathLinks.REGISTER}/:path*`
  ]
};
