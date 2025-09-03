'use client';

import { useEffect } from 'react';
import { useAuth } from '@/store/useAuth';

export default function AuthHydrator() {
  const { init } = useAuth();

  useEffect(() => {
    init();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return null;
}
