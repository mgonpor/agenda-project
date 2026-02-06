import { HttpClient } from "@angular/common/http";
import { inject, Injectable, signal } from "@angular/core";
import { environment } from "../../../../environments/environment";
import { StorageService } from "../../../core/services/storage.service";
import { AuthStatus } from "../../../core/models/auth-state.model";
import { LoginRequest, LoginResponse, RefreshDTO } from "../models/auth.model";
import { catchError, Observable, tap, throwError } from "rxjs";
import { JwtPayload } from '../../../core/models/auth-payload.model';
import { jwtDecode } from "jwt-decode";

@Injectable({ providedIn: 'root' })
export class AuthService {
    private http = inject(HttpClient);
    private storage = inject(StorageService);
    private readonly API_URL = `${environment.apiUrl}/auth`;

    // Usamos Signals para un estado reactivo moderno y eficiente
    authStatus = signal<AuthStatus>({
        token: null, userId: null, username: null, email: null, rol: null, isAuthenticated: false
    });

    constructor() {
        this.rehydrateState();
    }

    initializeAuth(): Promise<void> {
        return new Promise((resolve) => {
            this.rehydrateState(); // El método que ya teníamos para leer de storage
            resolve();
        });
    }

    login(credentials: LoginRequest): Observable<LoginResponse> {
        return this.http.post<LoginResponse>(`${this.API_URL}/auth/login`, credentials).pipe(
            tap(response => {
                this.storage.saveSession(response);
                this.hydrateState(response.access);
            })
        );
    }

    refresh(): Observable<LoginResponse> {
        const session = this.storage.getSession();
        if (!session || !session.refresh) {
            return throwError(() => new Error('No refresh token available'));
        }

        const dto: RefreshDTO = { refresh: session.refresh };

        return this.http.post<LoginResponse>(`${this.API_URL}/auth/refresh`, dto).pipe(
            tap(response => {
                this.storage.saveSession(response); // Guardamos el nuevo par de tokens
                this.hydrateState(response.access); // Actualizamos el estado global
            }),
            catchError(err => {
                this.logout(); // Si el refresh falla (ej. expiró), fuera
                return throwError(() => err);
            })
        );
    }

    logout(): void {
        this.storage.clean();
        this.authStatus.set({
            token: null, userId: null, username: null, email: null, rol: null, isAuthenticated: false
        });
    }

    private rehydrateState(): void {
        const session = this.storage.getSession();
        if (session) this.hydrateState(session.access);
    }

    private hydrateState(token: string): void {
        const decoded = jwtDecode<JwtPayload>(token);
        this.authStatus.set({
            token,
            userId: decoded.id,
            username: decoded.sub,
            email: decoded.email,
            rol: decoded.rol,
            isAuthenticated: true
        });
    }
}