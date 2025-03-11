import ObjectCard from '@/components/card/ObjectCard';

type MessierListProps = {
	objects: Record<string, MessierObject>
}

export default async function MessierList({objects}: MessierListProps) {
	return (
		<section className="grid grid-cols-1 gap-4 md:grid-cols-3 lg:grid-cols-4 mx-auto">
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