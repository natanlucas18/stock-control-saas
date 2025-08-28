'use client'
import Link from 'next/link';
import { useState } from 'react';
import { FaInstagram, FaLinkedin } from 'react-icons/fa6'


export default function App() {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <>
      <nav className="bg-gray-900 text-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center h-16">
            <div className="flex-shrink-0">
              <h1 className="text-xl font-bold">
                RevoApp
              </h1>
            </div>

            {/* Desktop Menu */}
            <div className="hidden md:flex space-x-4">
              <Link href="/Products" className="hover:text-gray-300">
                Preços
              </Link>
              <Link href="/Session" className="hover:text-gray-300">
                Sobre nós
              </Link>
              <Link href="/Login" className="hover:text-gray-300">
                Entrar
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
          <div className="md:hidden h-[30vh] w-full absolute bg-gray-900 top-14">
            <Link href="/Price" className="block px-4 py-2 hover:bg-blue-400">
              Preços
            </Link>
            <Link href="/About" className="block px-4 py-2 hover:bg-blue-400">
              Sobre nós
            </Link>
            <Link href="/Login" className="block px-4 py-2 hover:bg-blue-400">
              Entrar
            </Link>
          </div>
        )}
      </nav >
      <main className='flex flex-col'>
        <section className="font-sans h-[91vh] bg-gray-900 flex flex-col items-center justify-items-center sm:p-18 rounded-b-[20px]">
          <h2 className='text-center text-[1.5rem] font-bold mt-4 ml-2 mr-2 text-white'>Ferramenta para facilitar a <span className='text-gray-500'>organização<br />e o controle</span> do estoque da sua empresa!</h2>
        </section>
        <section className='w-full h-[100vh] flex flex-col bg-white'>

        </section>
      </main>
      <footer className="bg-white">
        <div className="mx-auto w-full max-w-screen-xl p-4 py-6 lg:py-8">
          <div className="md:flex md:justify-between">
            <div className="mb-6 md:mb-0">
              <a href="https://flowbite.com/" className="flex items-center">
                <span className="text-sm text-black sm:text-center dark:text-gray-400">©2025 RevoApp.
                </span>
              </a>
            </div>
            <div className="sm:flex sm:items-center sm:justify-between">
              <div className="flex mt-4 sm:justify-center sm:mt-0">
                <a href="#" className="text-black hover:text-gray-800 dark:hover:text-white ms-5">
                  <span className="w-4 h-4">
                    <FaInstagram />
                  </span>
                  <span className="sr-only">Instagram</span>
                </a>
                <a href="#" className="text-black hover:text-gray-800 dark:hover:text-white ms-5">
                  <span className="w-4 h-4">
                    <FaLinkedin />
                  </span>
                  <span className="sr-only">Linkedin</span>
                </a>
              </div>
            </div>
          </div>
        </div>
      </footer>
    </>
  );
}


