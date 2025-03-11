import { TopBar } from '@/components/menubar/TopBar';

export default function DashboardLayout({children}: { children: React.ReactNode }) {
	return (
		<div>
			<TopBar />
			<div className="flex justify-center">
				{children}
			</div>
		</div>
	)
}