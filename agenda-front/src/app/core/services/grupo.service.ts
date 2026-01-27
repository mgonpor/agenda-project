import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Grupo } from '../models/grupo.model';
import { Clase } from '../models/clase.model';
import { Anotacion } from '../models/anotacion.model';

@Injectable({
    providedIn: 'root'
})
export class GrupoService {
    private apiUrl = 'http://localhost:8080/api/grupos';

    constructor(private http: HttpClient) { }

    // CRUD
    create(grupo: Partial<Grupo>): Observable<Grupo> {
        return this.http.post<Grupo>(this.apiUrl, grupo);
    }

    getAll(): Observable<Grupo[]> {
        return this.http.get<Grupo[]>(this.apiUrl);
    }

    getById(id: number): Observable<Grupo> {
        return this.http.get<Grupo>(`${this.apiUrl}/${id}`);
    }

    update(id: number, grupo: Partial<Grupo>): Observable<Grupo> {
        return this.http.put<Grupo>(`${this.apiUrl}/${id}`, grupo);
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }

    // Related entities
    getClases(grupoId: number): Observable<Clase[]> {
        return this.http.get<Clase[]>(`${this.apiUrl}/${grupoId}/clases`);
    }

    getAnotaciones(grupoId: number): Observable<Anotacion[]> {
        return this.http.get<Anotacion[]>(`${this.apiUrl}/${grupoId}/anotaciones`);
    }
}
