import { PathLinks } from '@/types/path-links';
import {
  ArrowRightLeftIcon,
  ClipboardIcon,
  ClipboardListIcon,
  LayoutDashboardIcon,
  LucideIcon,
  MapPinIcon,
  MapPinPlusIcon,
  PackagePlusIcon,
  PackageSearchIcon
} from 'lucide-react';

export type Role = 'ROLE_ADMIN' | 'ROLE_USER' | 'ROLE_DEV';

export interface AppRoute {
  label: string;
  path: string;
  icon: LucideIcon;
  roles: Role[];
  children?: {
    label: string;
    path: string;
    icon: LucideIcon;
  }[];
}

export const APP_ROUTES: AppRoute[] = [
  {
    label: 'Dashboard',
    path: PathLinks.DASHBOARD,
    icon: LayoutDashboardIcon,
    roles: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_DEV']
  },
  {
    label: 'Produtos',
    path: PathLinks.LIST_PRODUCTS,
    icon: PackageSearchIcon,
    roles: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_DEV']
  },
  {
    label: 'Criar Produto',
    path: PathLinks.CREATE_PRODUCT,
    icon: PackagePlusIcon,
    roles: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_DEV']
  },
  {
    label: 'Localização de Estoque',
    path: PathLinks.LIST_STOCK_LOCATIONS,
    icon: MapPinIcon,
    roles: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_DEV']
  },
  {
    label: 'Criar Localização de Estoque',
    path: PathLinks.CREATE_STOCK_LOCATION,
    icon: MapPinPlusIcon,
    roles: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_DEV']
  },
  {
    label: 'Movimentações',
    path: PathLinks.MOVEMENTS,
    icon: ArrowRightLeftIcon,
    roles: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_DEV']
  },
  {
    label: 'Relatórios',
    path: '',
    icon: ClipboardIcon,
    roles: ['ROLE_ADMIN', 'ROLE_DEV'],
    children: [
      {
        label: 'Produtos',
        path: PathLinks.PRODUCTS_REPORT,
        icon: ClipboardListIcon
      },
      {
        label: 'Movimentações',
        path: PathLinks.MOVEMENTS_REPORT,
        icon: ClipboardListIcon
      }
    ]
  }
];
