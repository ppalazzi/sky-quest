import { TopBar } from '@/components/menubar/TopBar';
import Sidebar from '@/components/sidebar/Sidebar';

export default function DashboardLayout({ children }: { children: React.ReactNode }) {
  return (
    <div className="flex flex-col min-h-screen">
      <header className="flex justify-center sticky top-0 w-full">
        <TopBar />
      </header>

      <aside className="flex flex-1">
        <Sidebar />
      </aside>

      <div className="flex justify-center">{children}</div>
    </div>
  );
}
