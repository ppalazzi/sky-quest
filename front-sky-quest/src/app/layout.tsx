import type { Metadata } from 'next';
import './globals.css';

export const metadata: Metadata = {
  title: 'Sky Quest',
  description: 'Keep track of your personal observations of the sky.',
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className={`antialiased`}>{children}</body>
    </html>
  );
}
