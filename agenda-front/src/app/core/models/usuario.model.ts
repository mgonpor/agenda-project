export interface Usuario {
    id: number;
    username: string;
    email: string;
    password?: string; // Optional because we might not receive it from API, but send it for auth
    nombre: string;
}
