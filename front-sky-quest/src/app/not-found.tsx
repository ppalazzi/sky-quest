import React from 'react';
import Link from 'next/link';
import { Telescope } from 'lucide-react';

export const NotFound = () => {
  return (
    <div className="flex h-screen flex-col overflow-hidden">
      <header className="w-full border-b bg-background/95 backdrop-blur">
        <div className="container flex h-14 items-center">
          <div className="mr-4 flex">
            <Link href="/" className="mr-6 flex items-center space-x-2">
              <Telescope className="h-6 w-6" />
              <span className="font-bold">SkyQuest</span>
            </Link>
          </div>
        </div>
      </header>

      <main className="flex-1 flex items-center justify-center">
        <div className="w-full max-w-[700px] px-4 md:px-6 mx-auto">
          <div className="flex flex-col items-center justify-center text-center">
            <div className="text-5xl font-bold text-muted-foreground mb-2">404</div>
            <h1 className="text-2xl font-bold tracking-tighter sm:text-3xl md:text-4xl mb-3">
              Page Not Found
            </h1>
            <p className="mx-auto max-w-[600px] text-muted-foreground md:text-lg mb-6">
              Looks like this celestial object has drifted out of our observable universe. The page
              you&#39;re looking for doesn&#39;t exist or has been moved to a different orbit.
            </p>
            <div className="text-sm text-muted-foreground">
              <p>
                If you believe this is an error, please{' '}
                <Link href="/" className="underline underline-offset-4 hover:text-foreground">
                  contact support
                </Link>
              </p>
            </div>
          </div>
        </div>
      </main>

      <footer className="border-t py-4">
        <div className="container mx-auto">
          <p className="text-sm text-muted-foreground text-center">
            &copy; {new Date().getFullYear()} SkyQuest. All rights reserved.
          </p>
        </div>
      </footer>
    </div>
  );
};

export default NotFound;
