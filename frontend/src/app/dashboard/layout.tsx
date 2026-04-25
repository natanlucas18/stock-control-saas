import AppSidebar from '@/components/app-sidebar';
import { ToggleTheme } from '@/components/toggle-theme';
import { SidebarProvider, SidebarTrigger } from '@/components/ui/sidebar';

export default async function DashboardLayout({
  children
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <SidebarProvider>
      <AppSidebar />
      <div className='p-4 w-full space-y-4'>
        <header className='flex items-center justify-between gap-4'>
          <SidebarTrigger />
          <ToggleTheme />
        </header>
        <main className='container mx-auto'>{children}</main>
      </div>
    </SidebarProvider>
  );
}
