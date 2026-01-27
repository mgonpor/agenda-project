import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../models/usuario.model';
import { Grupo } from '../models/grupo.model';

@Injectable({
    providedIn: 'root'
})
export class UsuarioService {
    private apiUrl = 'http://localhost:8080/api/usuarios';

    constructor(private http: HttpClient) { }

    getMe(): Observable<Usuario> {
        return this.http.get<Usuario>(`${this.apiUrl}/me`);
    }

    updateMe(usuario: Partial<Usuario>): Observable<Usuario> {
        return this.http.put<Usuario>(`${this.apiUrl}/me`, usuario);
    }

    getMyGrupos(): Observable<Grupo[]> {
        return this.http.get<Grupo[]>(`${this.apiUrl}/me/grupos`);
    }
}
