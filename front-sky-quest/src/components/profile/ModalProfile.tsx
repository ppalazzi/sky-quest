import { Separator } from '@/components/separator/Separator';
import { Settings, UserRound, LogOut } from 'lucide-react';
import Link from 'next/link';

type ModalProfileProps = {
  isAuth: boolean;
};

export const ModalProfile = ({ isAuth }: ModalProfileProps) => {
  return (
    <div className={'absolute w-52 top-14 right-8 rounded-md border shadow-lg bg-white z-[999]'}>
      <ul className="p-4 space-y-2 w-full">
        {isAuth && (
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
            href="/"
          >
            <UserRound size={14} />
            <span className="text-sm">Profile</span>
          </Link>
        </li>
      </ul>
    </div>
  );
};
