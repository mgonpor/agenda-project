import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Clase } from '../models/clase.model';

@Injectable({
    providedIn: 'root'
})
export class ClaseService {
    private apiUrl = 'http://localhost:8080/api/clases';

    constructor(private http: HttpClient) { }

    create(clase: Partial<Clase>): Observable<Clase> {
        return this.http.post<Clase>(this.apiUrl, clase);
    }

    getAll(): Observable<Clase[]> {
        return this.http.get<Clase[]>(this.apiUrl);
    }

    getById(id: number): Observable<Clase> {
        return this.http.get<Clase>(`${this.apiUrl}/${id}`);
    }

    update(id: number, clase: Partial<Clase>): Observable<Clase> {
        return this.http.put<Clase>(`${this.apiUrl}/${id}`, clase);
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}
