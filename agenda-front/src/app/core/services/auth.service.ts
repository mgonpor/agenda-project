import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Usuario } from '../models/usuario.model';
import { environment } from '../../../environments/environment';

interface LoginResponse {
    token: string;
    usuario: Usuario; // Assuming API returns user details on login
}

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    // Assuming environment has apiUrl, using a placeholder for now or hardcoded if needed.
    // Ideally: private apiUrl = environment.apiUrl + '/auth';
    // For now I'll assume /api/auth
    private apiUrl = 'http://localhost:8080/api/auth';
    private tokenKey = 'authToken';

    constructor(private http: HttpClient) { }

    login(credentials: { username: string, password: string }): Observable<LoginResponse> {
        return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials).pipe(
            tap(response => this.setToken(response.token))
        );
    }

    // Assuming register is similar
    register(usuario: Usuario): Observable<any> {
        return this.http.post(`${this.apiUrl}/register`, usuario);
    }

    setToken(token: string): void {
        localStorage.setItem(this.tokenKey, token);
    }

    getToken(): string | null {
        return localStorage.getItem(this.tokenKey);
    }

    logout(): void {
        localStorage.removeItem(this.tokenKey);
    }

    isAuthenticated(): boolean {
        return !!this.getToken();
    }
}
