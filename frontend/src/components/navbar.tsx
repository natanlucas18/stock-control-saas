import { DesktopMenu } from './desktop-menu';
import MobileMenu from './mobile-menu';

export default function Navbar() {
  return (
    <header>
      <nav className='bg-blue-900 text-white'>
        <div className='max-w-7xl mx-auto px-4 sm:px-6 lg:px-8'>
          <div className='flex justify-between items-center h-16'>
            <div className='flex-shrink-0'>
              <h1 className='text-xl font-bold'>RevoApp</h1>
            </div>

            <div className='hidden md:flex space-x-4'>
              <DesktopMenu />
            </div>

            <div className='md:hidden'>
              <MobileMenu />
            </div>
          </div>
        </div>
      </nav>
    </header>
  );
}
