import api from '@/service/api';

export const catalogService = async (): Promise<Record<string, MessierObject>> => {
	const data = await api.get<Record<string, MessierObject>>('/catalog/messier');
	return data.data;
}