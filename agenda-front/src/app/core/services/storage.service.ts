import { Injectable } from "@angular/core";
import { LoginResponse } from "../../features/auth/models/auth.model";
import { environment } from "../../../environments/environment";

/**
 * Este servicio centraliza el acceso al almacenamiento del navegador.
 * Evita errores de "Magic Strings" usando constantes.
 */
@Injectable({
    providedIn: 'root'
})
export class StorageService {
    private readonly AUTH_KEY = environment.storageKeys.auth;

    saveSession(data: LoginResponse): void {
        localStorage.setItem(this.AUTH_KEY, JSON.stringify(data));
    }

    getSession(): LoginResponse | null {
        const data = localStorage.getItem(this.AUTH_KEY);
        return data ? JSON.parse(data) : null;
    }

    clean(): void {
        localStorage.clear();
    }
}