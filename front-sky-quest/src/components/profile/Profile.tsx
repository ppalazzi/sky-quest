'use client';

import { UserRound } from 'lucide-react';
import { useEffect, useRef, useState } from 'react';
import { ModalProfile } from '@/components/profile/ModalProfile';

export const Profile = () => {
  const [openModal, setOpenModal] = useState(false);
  const modalRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [openModal]);

  const handleClickOutside = (event: MouseEvent) => {
    if (modalRef.current && !modalRef.current.contains(event.target as Node)) {
      setOpenModal(false);
    }
  };

  return (
    <section className="flex flex-col justify-center items-center">
      <div className="flex items-center justify-center w-10 h-10 rounded-full bg-gray-100">
        <UserRound
          size={24}
          color="white"
          onClick={() => setOpenModal((openModal) => !openModal)}
        />
      </div>
      {openModal && (
        <div ref={modalRef}>
          <ModalProfile />
        </div>
      )}
    </section>
  );
};
