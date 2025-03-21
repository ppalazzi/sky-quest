import api from '@/service/api';

export const catalogService = async (): Promise<Record<string, MessierObject>> => {
	console.log('retrieving messier catalog from service');
	const data = await api.get<Record<string, MessierObject>>('/catalog/messier');
	return data.data;
}
