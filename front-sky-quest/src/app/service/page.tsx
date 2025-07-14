'use client';

import { useAuth, useAuthStore } from '@/store/useAuth';

export default function Service() {
  const carlos = useAuthStore((state) => state.user);

  const { user } = useAuth();

  return (
    <section className="bg-red-300 grid grid-cols-1 gap-4 md:grid-cols-3 lg:grid-cols-4">
      <p>
        USer name {user?.email} {carlos ? 'existo' : 'no existo amigo'}
      </p>
    </section>
  );
}
