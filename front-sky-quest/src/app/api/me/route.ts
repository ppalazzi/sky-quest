import { NextResponse } from 'next/server';
import api from '@/service/api';
import { AxiosError } from 'axios';

export type MeResponse = {
  user: User | null;
  status: number;
  message?: string;
};

export async function GET(): Promise<NextResponse<MeResponse>> {
  try {
    const response = await api.get('/user/me');

    return NextResponse.json<MeResponse>({
      user: response.data,
      status: response.status,
      message: 'OK',
    });
  } catch (error) {
    const err = error as AxiosError;
    const status = err.response?.status || 500;
    return NextResponse.json<MeResponse>(
      {
        user: null,
        status,
        message: status === 401 ? 'Unauthorized' : 'Error fetching current user',
      },
      { status },
    );
  }
}
