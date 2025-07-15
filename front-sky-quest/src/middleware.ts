import { NextResponse } from 'next/server';
import type { NextRequest } from 'next/server';

const publicRoutes = ['/login', '/register', '/'];

// This function can be marked `async` if using `await` inside
export function middleware(request: NextRequest) {
  const token = request.cookies.get('jwt')?.value;

  const path = request.nextUrl.pathname;

  // Define which paths are considered public (accessible without authentication)
  const nonePublicPath = publicRoutes.some((route) => path.startsWith(route));

  // Redirect logic
  if (nonePublicPath && !token) {
    // User is trying to access a protected route without being authenticated
    // Redirect them to the login page
    return NextResponse.redirect(new URL('/login', request.url));
  }

  // Continue with the request if no redirection is needed
  return NextResponse.next();
}

// Specify which paths this middleware should run on
export const config = {
  matcher: ['/service/:path*', '/dashboard/:path*'],
};
