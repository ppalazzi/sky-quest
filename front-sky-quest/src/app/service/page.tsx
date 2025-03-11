import ObjectCard from '@/components/card/ObjectCard';
import { catalogService } from '@/service/catalogService';


export default async function Service() {

	const objects = await catalogService();

	return (
		<section className="bg-red-300 grid grid-cols-1 gap-4 md:grid-cols-3 lg:grid-cols-4">
			{
				Object.entries(objects).map(([key, value]) => {
					return (
						<ObjectCard key={key} name={key} value={value} />
					)
				})
			}
		</section>
	)
}