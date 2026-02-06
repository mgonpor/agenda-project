import { HttpErrorResponse, HttpInterceptorFn } from "@angular/common/http";
import { inject } from "@angular/core";
import { AuthService } from "../../features/auth/services/auth.service";
import { catchError, switchMap, throwError } from "rxjs";
import { Router } from "@angular/router";

export const authInterceptor: HttpInterceptorFn = (req, next) => {
    const authService = inject(AuthService);
    const router = inject(Router);
    const { token, rol } = authService.authStatus();

    // 1. MAPEADO DE SEGURIDAD (Configuración automática)
    const roleRules: Record<string, string> = {
        '/admin/': 'ADMIN',
        // Puedes añadir más: '/editor/': 'EDITOR'
    };

    // 2. VERIFICACIÓN DE ROL PRE-VUELO
    // Buscamos si la URL de la petición coincide con alguna regla de rol
    const requiredRole = Object.keys(roleRules).find(path => req.url.includes(path));

    if (requiredRole && rol !== roleRules[requiredRole]) {
        console.error(`Bloqueo Interceptor: Se requiere ${roleRules[requiredRole]} pero eres ${rol}`);
        // Opcional: Redirigir o lanzar error. Aquí simplemente cancelamos.
        return throwError(() => new Error('Acceso denegado por falta de privilegios.'));
    }

    // Clonamos la petición para añadir el header si hay token
    let authReq = req;
    if (token) {
        authReq = req.clone({
            setHeaders: {
                Authorization: `Bearer ${token}`
            }
        });
    }

    return next(authReq).pipe(
        catchError((error: HttpErrorResponse) => {
            // Si el error es 401 y no es la petición de login/refresh
            if (error.status === 401 && !req.url.includes('/auth/')) {
                return authService.refresh().pipe(
                    switchMap((newResponse) => {
                        // Reintentamos la petición original con el nuevo token
                        const retryReq = req.clone({
                            setHeaders: { Authorization: `Bearer ${newResponse.access}` }
                        });
                        return next(retryReq);
                    }),
                    catchError((refreshError) => {
                        console.error('Fallo al renovar el token. Cerrando sesión...');
                        authService.logout();
                        router.navigate(['/auth/login']);
                        return throwError(() => refreshError);
                    })
                );
            }
            return throwError(() => error);
        })
    );
};