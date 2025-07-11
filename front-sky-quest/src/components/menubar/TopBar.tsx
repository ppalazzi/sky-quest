import { Profile } from '@/components/profile/Profile';

export const TopBar = () => {
  return (
    <div className="flex w-full items-center h-16 border-b bg-background px-4 md:px-6">
      <div>
        <span className="font-bold">SkyQuest</span>
      </div>

      <div className="ml-auto">
        <Profile />
      </div>
    </div>
  );
};
