import { HttpErrorResponse, HttpInterceptorFn } from "@angular/common/http";
import { inject } from "@angular/core";
import { catchError, throwError } from "rxjs";
import { ToastService } from "../services/toast.service";

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
    const toastService = inject(ToastService);

    return next(req).pipe(
        catchError((error: HttpErrorResponse) => {
            // 403: El usuario está logueado pero intenta ver algo que no le pertenece
            if (error.status === 403) {
                toastService.warning('Permisos insuficientes para esta acción.');
            }

            // 500: El servidor de Java ha tenido un problema
            if (error.status >= 500) {
                toastService.error('Error crítico en el servidor. Por favor, inténtelo de nuevo más tarde.');
            }

            return throwError(() => error);
        })
    );
};