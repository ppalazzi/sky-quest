import { Avatar, AvatarImage } from '@/components/ui/avatar';
import { AvatarFallback } from '@/components/ui/avatar';

export const Profile = () => {
	return (
		<div>
			<Avatar className="h-8 w-8">
				<AvatarImage src="https://github.com/shadcn.png" />
				<AvatarFallback>CN</AvatarFallback>
			</Avatar>
		</div>
	);
}