'use client';
import { signOut } from 'next-auth/react';

export default function ButtonLogout() {
  return (
    <button
      onClick={() => signOut()}
      className='bg-red-600 text-white px-6 py-1 text-5x1 rounded-[7px] h-8 w-18 cursor-pointer hover:bg-red-800 transition-colors'
    >
      Sair
    </button>
  );
}
