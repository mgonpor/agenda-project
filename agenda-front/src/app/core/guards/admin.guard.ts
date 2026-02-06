import { CanActivateFn, Router } from "@angular/router";
import { AuthService } from "../../features/auth/services/auth.service";
import { inject } from "@angular/core";

export const adminGuard: CanActivateFn = () => {
    const authService = inject(AuthService);
    const router = inject(Router);
    const { isAuthenticated, rol } = authService.authStatus();

    // Verificamos ambas condiciones: estar logueado y tener el rol correcto
    if (isAuthenticated && rol === 'ADMIN') {
        return true;
    }

    // Si no es admin, abortamos navegación y mandamos al dashboard
    console.warn('Intento de acceso no autorizado a zona de administración.');
    return router.parseUrl('/dashboard');
};