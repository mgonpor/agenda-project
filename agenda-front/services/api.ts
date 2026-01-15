import axios from 'axios';
import { storage } from '../utils/storage';

// Use environment variable for API URL
// Fallback to localhost if not set
const BASE_URL = process.env.EXPO_PUBLIC_API_URL || 'http://localhost:8080';

const api = axios.create({
    baseURL: BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

api.interceptors.request.use(
    async (config) => {
        const token = await storage.getItem('jwt_token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

api.interceptors.response.use(
    (response) => response,
    async (error) => {
        if (error.response && error.response.status === 401) {
            // Handle unauthorized (e.g., token expired)
            // We might want to clear the token and/or redirect to login
            await storage.removeItem('jwt_token');
        }
        return Promise.reject(error);
    }
);

export default api;
