import axios from 'axios';
import { cookies } from 'next/headers';

// Create an Axios instance
const api = axios.create({
	baseURL: process.env.NEXT_PUBLIC_BACKEND_URL,
	timeout: 5000,
	withCredentials: true,
	headers: {
		'Content-Type': 'application/json',
	},
});

api.interceptors.request.use(async (config) => {
	if (typeof window === 'undefined') {
		const cookieStore = await cookies()
		const token = cookieStore.get('jwt')?.value;

		if (token) {
			config.headers['Cookie'] = `jwt=${token}`;
		}
	}

	return config;
}, (error) => {
	return Promise.reject(error);
});

export default api;