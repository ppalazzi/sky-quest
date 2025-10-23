type UserResponse = {
  user: User | null;
  status: number;
  message?: string;
};

interface User {
  id: number;
  username: string;
  password: string;
  email: string;
  phone: string;
  token: string;
}

interface Messier {
  info: Info;
  data: Record<string, MessierObject>;
}

interface MessierObject {
  messierNumber: number;
  name: string;
  alternateNames: string[];
  NGC: string;
  type: string;
  constellation: string;
  rightAscension: string;
  declination: string;
  magnitude: number;
  size: string;
  distance: number;
  viewingSeason: string;
  viewingDifficulty: string;
  image: string;
}

interface Info {
  description: string;
  credit: string;
  license: string;
  notice: string;
}
