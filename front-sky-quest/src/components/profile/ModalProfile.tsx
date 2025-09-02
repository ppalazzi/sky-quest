import { Separator } from '@/components/separator/Separator';
import { Settings, UserRound, LogOut } from 'lucide-react';
import Link from 'next/link';
import { useAuth } from '@/store/useAuth';
import { logoutAction } from '@/action/auth/auth-action';
import React from 'react';

export const ModalProfile = () => {
  const { user, logout } = useAuth();

  const handleLogout = async () => {
    logout();
    await logoutAction();
  };

  return (
    <div className={'absolute w-52 top-14 right-8 rounded-md border shadow-lg bg-white z-[999]'}>
      <ul className="p-4 space-y-2 w-full">
        {user && (
          <>
            <li>
              <p className="text-sm font-bold">Pablo Palazzi</p>
            </li>

            <li>
              <Separator />
            </li>

            <li
              className="flex justify-start items-center border-2 border-transparent hover:border-2
				 hover:bg-sidebar-accent	rounded-md transition-colors w-full  text-sm gap-2 cursor-pointer"
            >
              <Settings size={14} />
              <span className="text-sm">Dashboard</span>
            </li>

            <li>
              <Separator />
            </li>

            <li
              onClick={handleLogout}
              className="flex justify-start items-center border-2 border-transparent hover:border-2
				 hover:bg-sidebar-accent	rounded-md transition-colors w-full  text-sm gap-2 cursor-pointer"
            >
              <LogOut size={14} />
              <span className="text-sm">Logout</span>
            </li>
          </>
        )}

        <li key="profile">
          <Link
            className="flex justify-start items-center border-2 border-transparent hover:border-2
				 hover:bg-sidebar-accent	rounded-md transition-colors w-full  text-sm gap-2 cursor-pointer"
            href="/login"
          >
            <UserRound size={14} />
            <span className="text-sm">Profile</span>
          </Link>
        </li>
      </ul>
    </div>
  );
};
