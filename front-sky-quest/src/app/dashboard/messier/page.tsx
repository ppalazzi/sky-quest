import { catalogService } from '@/app/service/catalogService';

export async function MessierList() {

	const messierCatalog = catalogService();

	return (
		<div>Catalog Messier</div>
	)

}