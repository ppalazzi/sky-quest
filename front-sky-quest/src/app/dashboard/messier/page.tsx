import MessierList from '@modules/dashboard/messier/MessierList';
import api from '@/service/api';

export default async function MessierPage() {

	const response = await api.get<Record<string, MessierObject>>('/catalog/messier');

	return (
		<MessierList objects={response.data} />
	)

}