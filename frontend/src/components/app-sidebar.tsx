'use client';

import {
  Sidebar,
  SidebarContent,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem
} from '@/components/ui/sidebar';
import { PathLinks } from '@/types/path-links';

import { Activity, BarChart, Home, MapPinHouse, Package } from 'lucide-react';
import Link from 'next/link';

export default function AppSidebar() {
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
                    href={PathLinks.LIST_PRODUCTS}
                    className='flex items-center gap-2'
                  >
                    <Package/>
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
                    <MapPinHouse/>
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
                    <Activity/>
                    <span>Movimentações</span>
                  </Link>
                </SidebarMenuButton>
              </SidebarMenuItem>

              <SidebarMenuItem>
                <SidebarMenuButton asChild>
                  <Link
                    href={PathLinks.REPORTS}
                    className='flex items-center gap-2'
                  >
                    <BarChart/>
                    <span>Relatórios</span>
                  </Link>
                </SidebarMenuButton>
              </SidebarMenuItem>
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>
    </Sidebar>
  );
}
