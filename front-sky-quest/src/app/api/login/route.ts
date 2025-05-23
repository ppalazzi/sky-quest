import { NextResponse } from 'next/server';
import api from '@/service/api';
import { AxiosError } from 'axios';

export type UserResponse = {
	user: User | null;
	status: number;
	message?: string;
};

export async function POST(request: Request):Promise<NextResponse<UserResponse>> {
	try {
		const {username, password} = await request.json();

		const response = await api.post('/user', {
			username: username,
			password: password
		})

		// set cookie
		const setCookie = response.headers['set-cookie']?.[0];
		const res:NextResponse<UserResponse> = NextResponse.json({
			user: response.data,
			status: response.status,
			message: 'Login Succesful'
		});

		if (setCookie) {
			res.headers.set('set-cookie', setCookie);
		}
		return res;
	} catch (error) {
		const err = error as AxiosError;
		return NextResponse.json<UserResponse>({
			user: null,
			status: err.response?.status || 500,
			message: 'Invalid credentials',
		});
	}
}
