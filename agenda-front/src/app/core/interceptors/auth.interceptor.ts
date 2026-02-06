import { HttpErrorResponse, HttpInterceptorFn } from "@angular/common/http";
import { inject } from "@angular/core";
import { AuthService } from "../../features/auth/services/auth.service";
import { catchError, switchMap, throwError } from "rxjs";
import { Router } from "@angular/router";

export const authInterceptor: HttpInterceptorFn = (req, next) => {
    const authService = inject(AuthService);
    const router = inject(Router);
    const token = authService.authStatus().token;

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