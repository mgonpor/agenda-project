export interface User {
    id?: number; // Optional if not returned by auth endpoint initially
    nombre?: string;
    email: string;
    // Add other user fields as needed
}

export interface Grupo {
    id: number;
    nombre: string;
}

export interface Clase {
    id: number;
    idGrupo: number;
    // Assuming API returns string enum "MONDAY", "TUESDAY" etc. or 0-6
    diaSemana: string;
    tramo: number; // 1-12 or similar
    aula: string;
}

export interface Anotacion {
    id: number;
    // idGrupo might be implicit if fetched under a group
    fecha: string; // ISO date string YYYY-MM-DD
    texto: string;
}

export interface AuthResponse {
    token: string;
    // user info if provided, otherwise might need a separate fetch
}
