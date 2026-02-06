// Logica interna, no corresponde con la API
export interface AuthStatus {
    token: string | null;
    userId: number | null;
    username: string | null;
    email: string | null;
    rol: string | null;
    isAuthenticated: boolean;
}