'use client';

import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem
} from '@/components/ui/sidebar';
import { PathLinks } from '@/types/path-links';
import { getCookie } from 'cookies-next/client';
import {
  Activity,
  BarChart,
  Home,
  LayoutDashboardIcon,
  MapPinHouse,
  Package
} from 'lucide-react';
import { signOut } from 'next-auth/react';
import Link from 'next/link';
import { redirect } from 'next/navigation';
import { Button } from './ui/button';

export default function AppSidebar() {
  const userRoles = getCookie('userRoles');
  const isAdmin = userRoles?.includes('ROLE_ADMIN');
  const isDev = userRoles?.includes('ROLE_DEV');
  const token = getCookie('accessToken');

  return (
    <Sidebar collapsible='icon'>
      <SidebarContent>
        <SidebarGroup>
          <SidebarGroupLabel>Menu</SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu>
              <SidebarMenuItem>
                <SidebarMenuButton asChild>
                  <Link
                    href={PathLinks.HOME}
                    className='flex items-center gap-2'
                  >
                    <Home />
                    <span>Inicio</span>
                  </Link>
                </SidebarMenuButton>
              </SidebarMenuItem>

              <SidebarMenuItem>
                <SidebarMenuButton asChild>
                  <Link
                    href={PathLinks.DASHBOARD}
                    className='flex items-center gap-2'
                  >
                    <LayoutDashboardIcon />
                    <span>Dashboard</span>
                  </Link>
                </SidebarMenuButton>
              </SidebarMenuItem>

              <SidebarMenuItem>
                <SidebarMenuButton asChild>
                  <Link
                    href={PathLinks.LIST_PRODUCTS}
                    className='flex items-center gap-2'
                  >
                    <Package />
                    <span>Produtos</span>
                  </Link>
                </SidebarMenuButton>
              </SidebarMenuItem>

              <SidebarMenuItem>
                <SidebarMenuButton asChild>
                  <Link
                    href={PathLinks.LIST_STOCK_LOCATIONS}
                    className='flex items-center gap-2'
                  >
                    <MapPinHouse />
                    <span>Local de estoque</span>
                  </Link>
                </SidebarMenuButton>
              </SidebarMenuItem>

              <SidebarMenuItem>
                <SidebarMenuButton asChild>
                  <Link
                    href={PathLinks.MOVEMENTS}
                    className='flex items-center gap-2'
                  >
                    <Activity />
                    <span>Movimentações</span>
                  </Link>
                </SidebarMenuButton>
              </SidebarMenuItem>

              {isAdmin && (
                <SidebarMenuItem>
                  <SidebarMenuButton asChild>
                    <Link
                      href={PathLinks.REPORTS}
                      className='flex items-center gap-2'
                    >
                      <BarChart />
                      <span>Relatórios</span>
                    </Link>
                  </SidebarMenuButton>
                </SidebarMenuItem>
              )}

              {isDev && (
                <SidebarMenuItem>
                  <SidebarMenuButton asChild>
                    <Link
                      href={PathLinks.REGISTER}
                      className='flex items-center gap-2'
                    >
                      <BarChart />
                      <span>Registro</span>
                    </Link>
                  </SidebarMenuButton>
                </SidebarMenuItem>
              )}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>
      <SidebarFooter>
        {token ? (
          <Button
            onClick={() => {
              signOut({ callbackUrl: '/login' });
            }}
          >
            Sair
          </Button>
        ) : (
          <Button onClick={() => redirect('/login')}>Entrar</Button>
        )}
      </SidebarFooter>
    </Sidebar>
  );
}
