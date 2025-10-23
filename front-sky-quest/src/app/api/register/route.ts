import api from '@/service/api';
import { NextResponse } from 'next/server';
import { AxiosError } from 'axios';

export async function POST(request: Request): Promise<NextResponse<UserResponse>> {
    try {
      const { username, password, email } = await request.json();

      const response = await api.post('/user/register', {
        username,
        password,
        email
      });

      return NextResponse.json({
        user: response.data,
        status: response.status,
        message: 'Register Successful',
      });
    }
    catch (error) {
      const err = error as AxiosError;
      return NextResponse.json<UserResponse>({
        user: null,
        status: err.response?.status || 500,
        message: 'Invalid credentials',
      });
    }
}