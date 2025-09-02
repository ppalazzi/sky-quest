import type { Metadata } from 'next';
import './globals.css';

export const metadata: Metadata = {
  title: 'Sky Quest',
  description: 'Keep track of your personal observations of the sky.',
};

import AuthHydrator from './AuthHydrator';

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className={`antialiased`}>
        <AuthHydrator />
        {children}
      </body>
    </html>
  );
}
