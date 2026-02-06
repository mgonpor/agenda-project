// Logica interna, no corresponde con la API
export interface AuthState {
    accessToken: string | null;
    isAuthenticated: boolean;
    role: 'ADMIN' | 'USER' | null; // Esto se extraerá del JWT decodificado
}