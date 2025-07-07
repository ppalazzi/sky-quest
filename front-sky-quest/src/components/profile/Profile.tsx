'use client';

import { UserRound } from 'lucide-react';
import { useState } from 'react';
import { ModalProfile } from '@/components/profile/ModalProfile';

export const Profile = () => {

	const [openModal, setOpenModal] = useState(false);

	return (
		<section className="flex flex-col justify-center items-center">
			<div className="flex items-center justify-center w-10 h-10 rounded-full bg-gray-100">
				<UserRound size={24} color="white" onClick={() => setOpenModal(openModal => !openModal)}/>
			</div>
			<ModalProfile openModal={openModal}/>
		</section>
	);
}
