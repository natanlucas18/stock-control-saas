'use client';
import Link from 'next/link';
import * as React from 'react';
import { AiOutlineStock } from 'react-icons/ai';
import { FaArrowCircleDown, FaArrowCircleUp, FaList } from 'react-icons/fa';
import { GrAddCircle } from 'react-icons/gr';
import { MdManageSearch } from 'react-icons/md';

import {
  NavigationMenu,
  NavigationMenuContent,
  NavigationMenuItem,
  NavigationMenuLink,
  NavigationMenuList,
  NavigationMenuTrigger
} from '@/components/ui/navigation-menu';
import { PathLinks } from '@/types/path-links';

export function Menu() {
  return (
    <NavigationMenu
      viewport={false}
      className='bg-blue-900'
    >
      <NavigationMenuList className='bg-blue-900'>
        <NavigationMenuItem className='bg-blue-900'>
          <NavigationMenuLink
            asChild
            className='bg-blue-900 font-bold'
          >
            <Link href='/Home'>Home</Link>
          </NavigationMenuLink>
        </NavigationMenuItem>

        <NavigationMenuItem>
          <NavigationMenuTrigger className='bg-blue-900'>
            Produtos
          </NavigationMenuTrigger>
          <NavigationMenuContent className='z-50'>
            <ul className='grid w-[300px] gap-4'>
              <li>
                <NavigationMenuLink asChild>
                  <Link
                    href={PathLinks.CREATE_PRODUCT}
                    className='flex-row items-center gap-2'
                  >
                    <GrAddCircle className='text-black' />
                    <div className='font-medium'>Novo Produto</div>
                  </Link>
                </NavigationMenuLink>
                <NavigationMenuLink asChild>
                  <Link
                    href={PathLinks.LIST_PRODUCTS}
                    className='flex-row items-center gap-2'
                  >
                    <FaList className='text-black' />
                    <div className='font-medium'>Listar Produtos</div>
                  </Link>
                </NavigationMenuLink>
              </li>
            </ul>
          </NavigationMenuContent>
        </NavigationMenuItem>

        <NavigationMenuItem>
          <NavigationMenuTrigger className='bg-blue-900'>
            Sessão
          </NavigationMenuTrigger>
          <NavigationMenuContent className='z-50'>
            <ul className='grid w-[200px] gap-4'>
              <li>
                <NavigationMenuLink asChild>
                  <Link
                    href='/CreateSection'
                    className='flex-row items-center gap-2'
                  >
                    <GrAddCircle className='text-black' />
                    <div className='font-medium'>Nova Sessão</div>
                  </Link>
                </NavigationMenuLink>
                <NavigationMenuLink asChild>
                  <Link
                    href='/Section'
                    className='flex-row items-center gap-2'
                  >
                    <FaList className='text-black' />
                    <div className='font-medium'>Listar Sessões</div>
                  </Link>
                </NavigationMenuLink>
              </li>
            </ul>
          </NavigationMenuContent>
        </NavigationMenuItem>

        <NavigationMenuItem>
          <NavigationMenuTrigger className='bg-blue-900'>
            Movimentações
          </NavigationMenuTrigger>
          <NavigationMenuContent className='z-50'>
            <ul className='grid w-[200px] gap-4'>
              <li>
                <NavigationMenuLink asChild>
                  <Link
                    href='/EntryProducts'
                    className='flex-row items-center gap-2'
                  >
                    <FaArrowCircleUp className='text-green-500' />
                    <div className='font-medium'>Entrada</div>
                  </Link>
                </NavigationMenuLink>
                <NavigationMenuLink asChild>
                  <Link
                    href='/Stock'
                    className='flex-row items-center gap-2'
                  >
                    <AiOutlineStock className='text-black' />
                    <div className='font-medium'>Estoque</div>
                  </Link>
                </NavigationMenuLink>
                <NavigationMenuLink asChild>
                  <Link
                    href='/ExitProducts'
                    className='flex-row items-center gap-2'
                  >
                    <FaArrowCircleDown className='text-red-500' />
                    <div className='font-medium'>Saida</div>
                  </Link>
                </NavigationMenuLink>
              </li>
            </ul>
          </NavigationMenuContent>
        </NavigationMenuItem>

        <NavigationMenuItem>
          <NavigationMenuTrigger className='bg-blue-900'>
            Relatórios
          </NavigationMenuTrigger>
          <NavigationMenuContent className='z-50'>
            <ul className='grid w-[200px] gap-4'>
              <li>
                <NavigationMenuLink asChild>
                  <Link
                    href='/ReportFilter'
                    className='flex-row items-center gap-2'
                  >
                    <MdManageSearch className='text-black text-3xl' />
                    <div className='font-medium'>Buscar Histórico</div>
                  </Link>
                </NavigationMenuLink>
              </li>
            </ul>
          </NavigationMenuContent>
        </NavigationMenuItem>
      </NavigationMenuList>
    </NavigationMenu>
  );
}

function ListItem({
  title,
  children,
  href,
  ...props
}: React.ComponentPropsWithoutRef<'li'> & { href: string }) {
  return (
    <li {...props}>
      <NavigationMenuLink asChild>
        <Link href={href}>
          <div className='text-sm leading-none font-medium'>{title}</div>
          <p className='text-muted-foreground line-clamp-2 text-sm leading-snug'>
            {children}
          </p>
        </Link>
      </NavigationMenuLink>
    </li>
  );
}
