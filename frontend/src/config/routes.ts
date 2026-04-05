import { PathLinks } from '@/types/path-links';
import {
  CalendarDays,
  CalendarPlus,
  CalendarSearch,
  LayoutDashboard,
  Scissors,
  Users
} from 'lucide-react';

export type Role = 'ROLE_ADMIN' | 'ROLE_USER' | 'ROLE_DEV';

export interface AppRoute {
  label: string;
  path: string;
  icon?: any;
  roles: Role[];
  children?: {
    label: string;
    path: string;
    icon: any;
  }[];
}

export const APP_ROUTES: AppRoute[] = [
  {
    label: 'Dashboard',
    path: PathLinks.DASHBOARD,
    icon: LayoutDashboard,
    roles: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_DEV']
  },
  {
    label: 'Produtos',
    path: PathLinks.LIST_PRODUCTS,
    icon: CalendarPlus,
    roles: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_DEV']
  },
  {
    label: 'Criar Produto',
    path: PathLinks.CREATE_PRODUCT,
    icon: CalendarSearch,
    roles: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_DEV']
  },
  {
    label: 'Localização de Estoque',
    path: PathLinks.LIST_STOCK_LOCATIONS,
    icon: CalendarDays,
    roles: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_DEV']
  },
  {
    label: 'Criar Localização de Estoque',
    path: PathLinks.CREATE_STOCK_LOCATION,
    icon: CalendarDays,
    roles: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_DEV']
  },
  {
    label: 'Movimentações',
    path: PathLinks.MOVEMENTS,
    icon: Scissors,
    roles: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_DEV']
  },
  {
    label: 'Relatórios',
    path: '',
    icon: Users,
    roles: ['ROLE_ADMIN', 'ROLE_DEV'],
    children: [
      {
        label: 'Produtos',
        path: PathLinks.PRODUCTS_REPORT,
        icon: Users
      },
      {
        label: 'Movimentações',
        path: PathLinks.MOVEMENTS_REPORT,
        icon: Users
      }
    ]
  }
];
