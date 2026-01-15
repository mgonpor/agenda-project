import { AuthResponse } from '../types';
import api from './api';

export const authService = {
    login: async (email: string, password: string): Promise<AuthResponse> => {
        const response = await api.post<AuthResponse>('/auth/authenticate', {
            email,
            password,
        });
        return response.data;
    },

    register: async (nombre: string, email: string, password: string): Promise<AuthResponse> => {
        const response = await api.post<AuthResponse>('/auth/register', {
            nombre,
            email,
            password,
        });
        return response.data;
    },
};
