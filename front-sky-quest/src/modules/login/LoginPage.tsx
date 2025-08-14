'use client';

import { useActionState, useEffect } from 'react';

import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { loginAction } from '@/action/auth/auth-action';
import { useAuth } from '@/store/useAuth';
import { useRouter } from 'next/navigation';

export type LoginState = {
  data?: User;
  error?: string;
};

export const Login = () => {
  const loginState: LoginState = {};
  const { login } = useAuth();
  const router = useRouter();

  const [state, action] = useActionState(loginAction, loginState);

  useEffect(() => {
    if (state?.data) {
      login(state.data);
      router.push('/dashboard/messier');
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [state?.data]);

  return (
    <section className="flex flex-col gap-6">
      <form action={action} className="flex flex-col gap-4">
        <div className="flex flex-col gap-2 ">
          <label className="text-sm font-medium">Username</label>
          <Input name="username" maxLength={50} />

          <label className="text-sm font-medium">Password</label>
          <Input name="password" type="password" maxLength={30} />

          {state?.error && (
            <div
              className="p-4 mb-4 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400"
              role="alert"
            >
              {state.error}.
            </div>
          )}
        </div>
        <Button className="w-full" type={'submit'}>
          Login
        </Button>
      </form>
    </section>
  );
};

export default Login;
