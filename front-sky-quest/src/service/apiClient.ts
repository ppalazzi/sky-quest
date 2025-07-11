import axios from 'axios';

const http = axios.create({
  baseURL: process.env.NEXT_PUBLIC_CLIENT_URL,
  timeout: 5000,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});

export default http;
