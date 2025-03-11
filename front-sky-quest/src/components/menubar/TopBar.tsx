import { Profile } from '@/components/profile/Profile';
import Link from 'next/link';

export const TopBar = () => {
	return (
		<div className="flex items-center justify-between border-2 border-solid">
			<span className="text-2xl">Sky Quest</span>

			<nav className="flex items-center space-x-4 lg:space-x-6">
				<Link className="text-sm font-medium transition-colors hover:text-primary" href="/dashboard/messier">Messier</Link>
				<Link className="text-sm font-medium transition-colors hover:text-primary" href="/">Ngc</Link>
			</nav>

			<div className="m-2">
				<Profile />
			</div>
		</div>
	)
}