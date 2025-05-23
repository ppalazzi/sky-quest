import { LoginState } from '@modules/login/LoginPage';
import { loginSchema } from '@/schema/validatorSchema';
import http from '@/service/apiClient';
import { UserResponse } from '@/app/api/login/route';
import { redirect } from 'next/navigation';

export const loginAction = async (state: LoginState, formData: FormData): Promise<LoginState> => {
	const userCredentials = {
		username: formData.get('username') as string,
		password: formData.get('password') as string,
	}

	// validate fields
	const validationFields = loginSchema.safeParse(userCredentials);
  if (!validationFields.success) {
	  state.error = validationFields.error.issues[0]?.message || "An error occurred.";
		return state;
  }

	// if no error then retrieve from backend
	const response = await http.post<UserResponse>('/api/login', {
		username: userCredentials.username,
		password: userCredentials.password
	});

  if (response.data.status === 200) {
	  redirect('/dashboard/messier')
  }
	else {
		state.error = response.data.message;
  }

	return state;
}