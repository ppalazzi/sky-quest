import { TopBar } from '@/components/menubar/TopBar';

export default function DashboardLayout({children}: { children: React.ReactNode }) {
	return (
		<main className="flex flex-col w-full h-screen">
			<TopBar />
			<section>
				{children}
			</section>
		</main>
	)
}