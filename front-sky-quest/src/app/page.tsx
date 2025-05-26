import { TopBar } from '@/components/menubar/TopBar';
import Sidebar from '@/components/sidebar/Sidebar';

export default function Home() {
	return (
		<div
			className="flex flex-col justify-items-center w-full min-h-screen">
			<header className="sticky top-0 w-full">
				<TopBar/>
			</header>

			<main className="flex-1 border-1 w-full h-full">
				<Sidebar />
			</main>

			<footer className="flex justify-center w-full mt-auto">
				<div className="container flex flex-col items-center justify-center gap-4 md:h-16 md:flex-row">
					<p className="text-sm text-muted-foreground">
						&copy; {new Date().getFullYear()} SkyQuest. All rights reserved.
					</p>
				</div>
			</footer>

		</div>
	);
}
