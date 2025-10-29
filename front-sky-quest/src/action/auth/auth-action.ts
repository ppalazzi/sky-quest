import { LoginState } from '@modules/login/LoginPage';
import { loginSchema, registerSchema } from '@/schema/validatorSchema';
import http from '@/service/apiClient';
import { redirect } from 'next/navigation';
import { RegisterState } from '@modules/register/Register';

export const loginAction = async (_state: LoginState, formData: FormData): Promise<LoginState> => {
  const newState: LoginState = {};

  const userCredentials = {
    username: formData.get('username') as string,
    password: formData.get('password') as string,
  };

  // validate fields
  const validationFields = loginSchema.safeParse(userCredentials);
  if (!validationFields.success) {
    newState.error = validationFields.error.issues[0]?.message || 'An error occurred.';
    return newState;
  }

  // if no error then retrieve from backend
  const response = await http.post<UserResponse>('/api/login', {
    username: userCredentials.username,
    password: userCredentials.password,
  });

  if (response.data.status === 200) {
    newState.data = response.data.user || undefined;
  } else {
    newState.error = response.data.message;
  }

  return newState;
};

export const logoutAction = async () => {
  const response = await http.post('/api/logout');
  if (response.data.logout) {
    console.info('logout');
  }

  redirect('/login');
};

export const registerAction = async (
  state: RegisterState,
  formData: FormData,
): Promise<LoginState> => {
  const newState: RegisterState = {};

  const userCredentials = {
    username: formData.get('username') as string,
    password: formData.get('password') as string,
    email: formData.get('email') as string,
  };

  // validate fields
  const validationFields = registerSchema.safeParse(userCredentials);
  if (!validationFields.success) {
    newState.error = validationFields.error.issues[0]?.message || 'An error occurred.';
    return newState;
  }

  // if validation succeeded then register user
  const response = await http.post<UserResponse>('/api/register', {
    username: userCredentials.username,
    password: userCredentials.password,
    email: userCredentials.email,
  });

  if (response.data.status !== 200) {
    newState.error = response.data.message;
    return newState;
  }

  redirect('/login');
};
