import AppSidebar from '@/components/app-sidebar';
import { ToggleTheme } from '@/components/toggle-theme';
import { SidebarProvider, SidebarTrigger } from '@/components/ui/sidebar';
import { getServerSession } from 'next-auth';

export default async function DashboardLayout({
  children
}: Readonly<{
  children: React.ReactNode;
}>) {
  const session = await getServerSession();

  return (
    <SidebarProvider>
      <AppSidebar />
      <div className='p-4 w-full space-y-4'>
        <header className='flex items-center justify-between gap-4'>
          <SidebarTrigger />
          <h1>{session?.user?.name}</h1>
          <ToggleTheme />
        </header>
        <main className=''>{children}</main>
      </div>
    </SidebarProvider>
  );
}
