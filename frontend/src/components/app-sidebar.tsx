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
  sidebarMenuButtonVariants,
  SidebarMenuItem
} from '@/components/ui/sidebar';
import { APP_ROUTES } from '@/config/routes';
import { cn } from '@/lib/utils';
import { useAuthStore } from '@/stores/auth-store';
import { LogOutIcon } from 'lucide-react';
import Link from 'next/link';
import { Button } from './ui/button';
import {
  Collapsible,
  CollapsibleContent,
  CollapsibleTrigger
} from './ui/collapsible';

export default function AppSidebar() {
  const { user } = useAuthStore((state) => state);
  const routes = APP_ROUTES.filter((route) => {
    return route.roles.some((r) => user?.role.includes(r));
  });

  return (
    <Sidebar collapsible='icon'>
      <SidebarContent>
        <SidebarGroup>
          <SidebarGroupLabel>Menu</SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu>
              {routes.map((route) =>
                route.children ? (
                  <Collapsible
                    key={route.path}
                    asChild
                  >
                    <SidebarMenuItem>
                      <CollapsibleTrigger
                        className={cn(
                          sidebarMenuButtonVariants(),
                          'cursor-pointer'
                        )}
                      >
                        {route.label}
                      </CollapsibleTrigger>
                      <CollapsibleContent>
                        {route.children.map((children) => (
                          <Link
                            className={cn(sidebarMenuButtonVariants(), 'pl-6')}
                            href={children.path}
                            key={children.path}
                          >
                            {children.label}
                          </Link>
                        ))}
                      </CollapsibleContent>
                    </SidebarMenuItem>
                  </Collapsible>
                ) : (
                  <SidebarMenuItem key={route.path}>
                    <SidebarMenuButton asChild>
                      <Link
                        href={route.path}
                        className='flex items-center gap-2'
                      >
                        <span>{route.label}</span>
                      </Link>
                    </SidebarMenuButton>
                  </SidebarMenuItem>
                )
              )}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>
      <SidebarFooter>
        <Button>
          <LogOutIcon />
        </Button>
      </SidebarFooter>
    </Sidebar>
  );
}
