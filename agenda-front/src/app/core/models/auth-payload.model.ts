export interface JwtPayload {
    sub: string;      // Generalmente el 'username'
    id: number;       // El 'id' de la entidad Usuario.java
    email: string;    // El 'email' único
    rol: string;      // Tu campo 'rol' (ADMIN, USER, etc.)
    iat: number;      // Timestamp creación
    exp: number;      // Timestamp expiración
}