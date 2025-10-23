import Login from '@modules/login/LoginPage';
import Link from 'next/link';

export default async function LoginPage() {
  return (
    <section className="container flex flex-col h-screen w-screen justify-center items-center mx-auto">
      <div className="flex flex-col justify-center space-y-6 sm:w-[350px]">
        <div className="flex flex-col text-center space-y-2">
          <h1 className="text-2xl font-semibold tracking-tight">Welcome Back</h1>
          <p className="text-sm text-muted-foreground">
            Enter your credentials to sign in to your account
          </p>
        </div>

        <Login />

        <p className="text-sm text-center text-muted-foreground">
          <Link className="hover:text-brand underline" href="/register">
            Don&apos;t have an account? Register
          </Link>
        </p>
      </div>
    </section>
  );
}
