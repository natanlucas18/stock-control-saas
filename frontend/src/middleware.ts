import type { NextRequest } from 'next/server';
import { NextResponse } from 'next/server';
import { PathLinks } from './types/path-links';
import { getToken } from 'next-auth/jwt';

const protectedRoutes = [
  PathLinks.DASHBOARD,
  PathLinks.CREATE_PRODUCT,
  PathLinks.LIST_PRODUCTS,
  PathLinks.CREATE_STOCK_LOCATION,
  PathLinks.LIST_STOCK_LOCATIONS,
  PathLinks.MOVEMENTS,
  PathLinks.REPORTS,
  PathLinks.SIGN_UP
];

export async function middleware(req: NextRequest) {
  const { pathname } = req.nextUrl;
  const isProtected = protectedRoutes.some((route) =>
    pathname.startsWith(route)
  );

  if (!isProtected) return NextResponse.next();

  const token = await getToken({
    req,
    secret: process.env.NEXTAUTH_SECRET
  });

  const buffer = 10 * 1000;
  const isExpired = token?.expiresAt ? (Date.now() + buffer > token.expiresAt) : true;
  
  if (!token?.accessToken || isExpired) {
    const loginUrl = new URL(PathLinks.SIGN_IN, req.url);
    loginUrl.searchParams.set('callbackUrl', req.nextUrl.pathname);
    return NextResponse.redirect(loginUrl);
  }
  
  const userRoles = token.userRoles as string[] | undefined

  const reportsRouter = ['/relatorios', '/sign-up'];
  const isAdmin = userRoles?.includes('ROLE_ADMIN');
  const isDev = userRoles?.includes('ROLE_DEV');

  if (
    reportsRouter.includes(pathname) &&
    pathname === PathLinks.REPORTS &&
    !isAdmin
  ) {
    return NextResponse.redirect(new URL(PathLinks.DASHBOARD, req.url));
  } else if (
    reportsRouter.includes(pathname) &&
    pathname === PathLinks.SIGN_UP &&
    !isDev
  ) {
    return NextResponse.redirect(new URL(PathLinks.DASHBOARD, req.url));
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
    `${PathLinks.SIGN_UP}/:path*`
  ]
};
