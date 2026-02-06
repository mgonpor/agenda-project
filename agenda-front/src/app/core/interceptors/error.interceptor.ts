import { HttpErrorResponse, HttpInterceptorFn } from "@angular/common/http";
import { catchError, throwError } from "rxjs";

export const errorInterceptor: HttpInterceptorFn = (req, next) => {

    return next(req).pipe(
        catchError((error: HttpErrorResponse) => {
            // 403: El usuario está logueado pero intenta ver algo que no le pertenece
            if (error.status === 403) {
                console.error('Permisos insuficientes para esta acción.');
            }

            // 500: El servidor de Java ha tenido un problema
            if (error.status >= 500) {
                console.error('Error crítico en el servidor.');
            }

            return throwError(() => error);
        })
    );
};