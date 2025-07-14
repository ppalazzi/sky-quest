import { LayoutDashboard, Telescope, CheckSquare, User } from 'lucide-react';
import Link from 'next/link';

const items = [
  {
    title: 'Dashboard',
    href: '/dashboard',
    icon: LayoutDashboard,
    requiresAuth: false,
  },
  {
    title: 'Observations',
    href: '/observations',
    icon: CheckSquare,
    requiresAuth: true,
  },
  {
    title: 'Equipment',
    href: '/equipment',
    icon: Telescope,
    requiresAuth: true,
  },
  {
    title: 'Profile',
    href: '/profile',
    icon: User,
    requiresAuth: false,
  },
];

const Sidebar = () => {
  return (
    <section className="flex h-full justify-start bg-sidebar p-2">
      <ul className="flex flex-col w-full mx-1 my-2 gap-4">
        {items.map((item) => {
          return (
            <li
              className="flex px-1 border-2 border-transparent hover:border-2
						hover:bg-sidebar-accent	rounded-md transition-colors"
              key={item.title}
            >
              <Link
                className="flex justify-center items-center text-sm gap-2
								"
                href={item.href}
              >
                <item.icon className="h-4 w-4" />
                <span>{item.title}</span>
              </Link>
            </li>
          );
        })}
      </ul>
    </section>
  );
};

export default Sidebar;
