'use client'

import { useState } from 'react';

import http from '@/service/apiClient';
import { useRouter } from 'next/navigation';

export const Login = () => {
	const router = useRouter();

	const [username, setUsername] = useState('');
	const [password, setPassword] = useState('');

	const handlerLogin = async () => {

		try {
			await http.post('/api/login', {
				username,
				password
			});

			router.push('/dashboard/messier');
		}
		catch (error) {
			console.error(error);
		}
	}

	return (
		<section className="flex flex-col gap-4">
			<article className="flex flex-col gap-4">
				<label>Username</label>
				<input type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
				<label>Password</label>
				<input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
			</article>
			<button onClick={() => handlerLogin()}>Login</button>
		</section>
	);
}

export default Login;