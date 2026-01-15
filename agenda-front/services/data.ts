import { Anotacion, Clase, Grupo } from '../types';
import api from './api';

export const dataService = {
    // Grupos
    getGrupos: async (): Promise<Grupo[]> => {
        const response = await api.get<Grupo[]>('/grupos');
        return response.data;
    },

    getGrupoById: async (id: number): Promise<Grupo> => {
        const response = await api.get<Grupo>(`/grupos/${id}`);
        return response.data;
    },

    // Clases
    getClases: async (): Promise<Clase[]> => {
        const response = await api.get<Clase[]>('/clases');
        return response.data;
    },

    getClasesByGrupo: async (idGrupo: number): Promise<Clase[]> => {
        const response = await api.get<Clase[]>(`/clases/grupo/${idGrupo}`);
        return response.data;
    },

    // Anotaciones
    getAnotacionesByGrupo: async (idGrupo: number): Promise<Anotacion[]> => {
        const response = await api.get<Anotacion[]>(`/grupos/${idGrupo}/anotaciones`);
        return response.data;
    },

    getAnotacionesByGrupoAndDate: async (idGrupo: number, fecha: string): Promise<Anotacion[]> => {
        const response = await api.get<Anotacion[]>(`/grupos/${idGrupo}/anotaciones/search`, {
            params: { fecha }
        });
        return response.data;
    },

    createAnotacion: async (idGrupo: number, fecha: string, texto: string): Promise<Anotacion> => {
        const response = await api.post<Anotacion>(`/grupos/${idGrupo}/anotaciones`, {
            id: 0,
            fecha,
            texto,
        });
        return response.data;
    },

    updateAnotacion: async (idGrupo: number, idAnotacion: number, fecha: string, texto: string): Promise<Anotacion> => {
        const response = await api.put<Anotacion>(`/grupos/${idGrupo}/anotaciones/${idAnotacion}`, {
            id: idAnotacion,
            fecha,
            texto,
        });
        return response.data;
    },

    deleteAnotacion: async (idGrupo: number, idAnotacion: number): Promise<void> => {
        await api.delete(`/grupos/${idGrupo}/anotaciones/${idAnotacion}`);
    },
};
