'use client';
import { Menu } from '@/components/nav-menu';
import Link from 'next/link';
import { useState } from 'react';
import { AiOutlineStock } from 'react-icons/ai';
import { FaArrowCircleDown, FaArrowCircleUp, FaList } from 'react-icons/fa';
import { GrAddCircle } from 'react-icons/gr';
import { MdManageSearch } from 'react-icons/md';

export default function Navbar() {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <header>
      <nav className='bg-blue-900 text-white'>
        <div className='max-w-7xl mx-auto px-4 sm:px-6 lg:px-8'>
          <div className='flex justify-between items-center h-16'>
            <div className='flex-shrink-0'>
              <h1 className='text-xl font-bold'>RevoApp</h1>
            </div>

            {/* Desktop Menu */}
            <div className='hidden md:flex space-x-4'>
              <Menu />
            </div>

            {/* Mobile Menu Button */}
            <div className='md:hidden'>
              <button
                onClick={() => setIsOpen(!isOpen)}
                className='focus:outline-none'
              >
                <svg
                  className='h-6 w-6'
                  xmlns='http://www.w3.org/2000/svg'
                  fill='none'
                  viewBox='0 0 24 24'
                  stroke='currentColor'
                >
                  {isOpen ? (
                    <path
                      strokeLinecap='round'
                      strokeLinejoin='round'
                      strokeWidth='2'
                      d='M6 18L18 6M6 6l12 12'
                    />
                  ) : (
                    <path
                      strokeLinecap='round'
                      strokeLinejoin='round'
                      strokeWidth='2'
                      d='M4 6h16M4 12h16m-7 6h7'
                    />
                  )}
                </svg>
              </button>
            </div>
          </div>
        </div>

        {/* Mobile Menu */}
        {isOpen && (
          <div className='md:hidden h-[90vh] w-full absolute bg-blue-900 top-14 z-1'>
            <Link
              href='/Home'
              className='block font-semibold px-4 py-2'
              onClick={() => setIsOpen(false)}
            >
              Home
            </Link>

            <div className='block px-4 py-2 text-gray-400 border-t-solid border-t border-gray-600'>
              Produtos
            </div>
            <Link
              href='/criar-produto'
              className='block font-semibold px-4 py-1 ml-2'
              onClick={() => setIsOpen(false)}
            >
              <div className='flex flex-row items-center gap-1'>
                <GrAddCircle />
                <div>Novo Produto</div>
              </div>
            </Link>
            <Link
              href='/Products'
              className='block font-semibold px-4 py-1 pb-2 ml-2'
              onClick={() => setIsOpen(false)}
            >
              <div className='flex flex-row items-center gap-1'>
                <FaList />
                <div>Listar Produtos</div>
              </div>
            </Link>

            <div className='block px-4 py-2 text-gray-400 border-t-solid border-t border-gray-600'>
              Sessão
            </div>
            <Link
              href='/CreateSection'
              className='block font-semibold px-4 ml-2 py-1'
              onClick={() => setIsOpen(false)}
            >
              <div className='flex flex-row items-center gap-1'>
                <GrAddCircle />
                <div>Nova Sessão</div>
              </div>
            </Link>
            <Link
              href='/Section'
              className='block font-semibold px-4 ml-2 pb-2 py-1'
            >
              <div className='flex flex-row items-center gap-1'>
                <FaList />
                <div>Listar Sessões</div>
              </div>
            </Link>

            <div className='block px-4 py-2 text-gray-400 border-t-solid border-t border-gray-600'>
              Movimentações
            </div>
            <Link
              href='/EntryStock'
              className='block font-semibold px-4 ml-2 py-1'
              onClick={() => setIsOpen(false)}
            >
              <div className='flex flex-row items-center gap-1'>
                <FaArrowCircleUp className='text-green-500' />
                <div>Entrada</div>
              </div>
            </Link>
            <Link
              href='/Stock'
              className='block font-semibold px-4 ml-2 py-1'
              onClick={() => setIsOpen(false)}
            >
              <div className='flex flex-row items-center gap-1'>
                <AiOutlineStock />
                <div>Estoque</div>
              </div>
            </Link>
            <Link
              href='/ExitStock'
              className='block font-semibold px-4 ml-2 pb-2 py-1'
              onClick={() => setIsOpen(false)}
            >
              <div className='flex flex-row items-center gap-1'>
                <FaArrowCircleDown className='text-red-500' />
                <div>Saida</div>
              </div>
            </Link>

            <div className='block px-4 py-2 text-gray-400 border-t-solid border-t border-gray-600'>
              Relatórios
            </div>
            <Link
              href='/History'
              className='block font-semibold px-4 ml-2 py-2'
              onClick={() => setIsOpen(false)}
            >
              <div className='flex flex-row items-center gap-1'>
                <MdManageSearch className='text-lg' />
                <div>Buscar histórico</div>
              </div>
            </Link>
          </div>
        )}
      </nav>
    </header>
  );
}
