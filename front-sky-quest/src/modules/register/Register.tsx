'use client';

import Link from 'next/link';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { useActionState } from 'react';
import { registerAction } from '@/action/auth/auth-action';

export type RegisterState = {
  data?: User;
  error?: string;
};

export const Register = () => {
  const registerState: RegisterState = {};

  const [state, action] = useActionState(registerAction, registerState);

  return (
    <section className="container flex flex-col h-screen w-screen justify-center items-center
    mx-auto gap-6">
      <Link className="absolute left-4 top-4 md:left-8 md:top-8" href="/">
        <span className="font-bold">SkyQuest</span>
      </Link>

      <div className="flex flex-col mx-auto">
        <h1 className="text-2xl font-semibold tracking-tight text-center">Create an account</h1>
        <p className="text-sm text-muted-foreground">
          {' '}
          Enter your information to create an account
        </p>
      </div>

      <form action={action} className="flex flex-col gap-4 sm:w-[350px]">
        <div className="flex flex-col gap-2">
          <label className="text-sm font-medium">Username</label>
          <Input name="username" maxLength={50} />

          <label className="text-sm font-medium">Email</label>
          <Input name="email" type="email" maxLength={255} />

          <label className="text-sm font-medium">Password</label>
          <Input name="password" type={"password"} maxLength={30} />
        </div>

        {state?.error && (
          <div
            className="p-4 mb-4 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400"
            role="alert"
          >
            {state.error}.
          </div>
        )}

        <Button className="w-full" type="submit">Create Account</Button>
      </form>

      <p className="text-sm text-muted-foreground">
        <Link className="hover:text-brand underline" href="/login">Already have an account? Sign in</Link>
      </p>
    </section>
  );
};
