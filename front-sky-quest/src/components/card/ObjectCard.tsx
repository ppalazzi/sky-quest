import Image from 'next/image'


type ObjectCardProps = {
	name: string,
	value: MessierObject
}

export default function ObjectCard({name, value}: ObjectCardProps) {
	return (
		<article key={name} className="flex justify-center">
			<div>
				<h2 className="text-lg font-bold">{name}</h2>
				<Image className="h-[300px] w-[300px] object-cover" src={value.image} width={300} height={300}
				       alt={value.name}/>
				<h3>{value.name}</h3>
				<p className="opacity-90">{value.constellation}</p>
			</div>
		</article>
	)
}