import { CanActivateFn, Router } from "@angular/router";
import { inject } from "@angular/core";
import { AuthService } from "../../features/auth/services/auth.service";

export const authGuard: CanActivateFn = () => {
    const authService = inject(AuthService);
    const router = inject(Router);

    // Si está autenticado, adelante.
    if (authService.authStatus().isAuthenticated) {
        return true;
    }

    // Si no, redirigimos al login de forma atómica
    return router.parseUrl('/auth/login');
};