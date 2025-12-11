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
  ActivityIcon,
  ClipboardListIcon,
  LayoutDashboardIcon,
  LogOutIcon,
  MapPinHouse,
  Package,
  UserPlus2Icon
} from 'lucide-react';
import { signOut } from 'next-auth/react';
import Link from 'next/link';
import { useEffect, useState } from 'react';
import { Button } from './ui/button';

export default function AppSidebar() {
  const userRoles = getCookie('userRoles');
  const [isAdmin, setIsAdmin] = useState(true);
  const [isDev, setIsDev] = useState(true);

  useEffect(() => {
    if (userRoles) {
      setIsAdmin(!userRoles.includes('ROLE_ADMIN'));
      setIsDev(!userRoles.includes('ROLE_DEV'));
    }
  }, [userRoles]);

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
                    <ActivityIcon />
                    <span>Movimentações</span>
                  </Link>
                </SidebarMenuButton>
              </SidebarMenuItem>

              <SidebarMenuItem hidden={isAdmin}>
                <SidebarMenuButton asChild>
                  <div className='flex items-center gap-2 cursor-pointer'>
                    <ClipboardListIcon />
                    <span>Relatórios</span>
                  </div>
                </SidebarMenuButton>
              </SidebarMenuItem>

              <SidebarMenuItem hidden={isDev}>
                <SidebarMenuButton asChild>
                  <Link
                    href={PathLinks.SIGN_UP}
                    className='flex items-center gap-2'
                  >
                    <UserPlus2Icon />
                    <span>Registro</span>
                  </Link>
                </SidebarMenuButton>
              </SidebarMenuItem>
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>
      <SidebarFooter>
        <Button
          onClick={() => {
            signOut({ callbackUrl: PathLinks.SIGN_IN });
          }}
        >
          <LogOutIcon />
        </Button>
      </SidebarFooter>
    </Sidebar>
  );
}
