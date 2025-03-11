import { catalogService } from '@/service/catalogService';
import MessierList from '@modules/dashboard/messier/MessierList';

export default async function MessierPage() {

	const messierCatalog = await catalogService();

	return (
		<MessierList objects={messierCatalog} />
	)

}