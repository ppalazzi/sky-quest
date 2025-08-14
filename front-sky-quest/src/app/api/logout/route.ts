import { NextResponse } from 'next/server';
import api from '@/service/api';

export type LogoutResponse = {
  logout: boolean;
};

export async function POST(): Promise<NextResponse<LogoutResponse>> {
  try {
    const response = await api.post('/user/logout');
    const ret: NextResponse<LogoutResponse> = NextResponse.json({ logout: false });

    if (response.status === 200) {
      const res: NextResponse<LogoutResponse> = NextResponse.json({
        logout: true,
      });

      res.headers.set(
        'Set-Cookie',
        `jwt=; Path=/; Expires=Thu, 01 Jan 1970 00:00:00 GMT; HttpOnly; Secure; SameSite=Strict`,
      );

      return res;
    }

    return ret;
  } catch (error) {
    console.error('Error logging out' + error);
    return NextResponse.json({ logout: false });
  }
}
