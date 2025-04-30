import { NextResponse } from 'next/server';
import api from '@/service/api';

export async function POST(request: Request) {
	try {
		const {username, password} = await request.json();

		const response = await api.post('/user', {
			username,
			password
		})

		// set cookie
		const setCookie = response.headers['set-cookie']?.[0];
		const res = NextResponse.json({
			user: response.data,
		});

		if (setCookie) {
			res.headers.set('set-cookie', setCookie);
		}

		return res;
	} catch (error) {
		console.error(error);
		return NextResponse.json({error: 'Server error'}, {status: 500});
	}
}
