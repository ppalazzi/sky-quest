import { LoginState } from '@modules/login/LoginPage';
import { loginSchema } from '@/schema/validatorSchema';
import http from '@/service/apiClient';
import { UserResponse } from '@/app/api/login/route';

export const loginAction = async (_state: LoginState, formData: FormData): Promise<LoginState> => {
	const newState:LoginState = {};

	const userCredentials = {
		username: formData.get('username') as string,
		password: formData.get('password') as string,
	}

	// validate fields
	const validationFields = loginSchema.safeParse(userCredentials);
  if (!validationFields.success) {
	  newState.error = validationFields.error.issues[0]?.message || "An error occurred.";
		return newState;
  }

	// if no error then retrieve from backend
	const response = await http.post<UserResponse>('/api/login', {
		username: userCredentials.username,
		password: userCredentials.password
	});

  if (response.data.status === 200) {
	  newState.data = response.data.user || undefined;
  }
	else {
	  newState.error = response.data.message;
  }

	return newState;
}