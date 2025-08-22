"use client"
import React, { useState } from "react";
import Link from 'next/link'

export default function Nav() {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <nav className="bg-blue-900 text-white">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          <div className="flex-shrink-0">
            <h1 className="text-xl font-bold">
              RevoApp
            </h1>
          </div>

          {/* Desktop Menu */}
          <div className="hidden md:flex space-x-4">
            <Link href="/Home" className="hover:text-gray-300">
              Inicio
            </Link>
            <Link href="/Products" className="hover:text-gray-300">
              Produtos
            </Link>
            <Link href="/Session" className="hover:text-gray-300">
              Sessão
            </Link>
            <Link href="/Stock" className="hover:text-gray-300">
              Estoque
            </Link>
            <Link href="/Hitory" className="hover:text-gray-300">
              Histórico
            </Link>
            <Link href="/" className="hover:text-gray-300">
              Sair
            </Link>
          </div>

          {/* Mobile Menu Button */}
          <div className="md:hidden">
            <button
              onClick={() => setIsOpen(!isOpen)}
              className="focus:outline-none"
            >
              <svg
                className="h-6 w-6"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                {isOpen ? (
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth="2"
                    d="M6 18L18 6M6 6l12 12"
                  />
                ) : (
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth="2"
                    d="M4 6h16M4 12h16m-7 6h7"
                  />
                )}
              </svg>
            </button>
          </div>
        </div>
      </div>

      {/* Mobile Menu */}
      {isOpen && (
        <div className="md:hidden h-[90vh] w-full absolute bg-blue-900 top-14 z-1">
          <Link href="/Home" className="block px-4 py-2 hover:bg-blue-400">
            Inicio
          </Link>
          <Link href="/Products" className="block px-4 py-2 hover:bg-blue-400">
            Produtos
          </Link>
          <Link href="/Session" className="block px-4 py-2 hover:bg-blue-400">
            Sessão
          </Link>
          <Link href="/Stock" className="block px-4 py-2 hover:bg-blue-400">
            Estoque
          </Link>
          <Link href="/History" className="block px-4 py-2 hover:bg-blue-400">
            Histórico
          </Link>
          <Link href="/" className="block px-4 py-2 hover:bg-blue-400">
            Sair
          </Link>
        </div>
      )}
    </nav >
  );
};