import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Anotacion } from '../models/anotacion.model';

@Injectable({
    providedIn: 'root'
})
export class AnotacionService {
    private apiUrl = 'http://localhost:8080/api/anotaciones';

    constructor(private http: HttpClient) { }

    create(anotacion: Partial<Anotacion>): Observable<Anotacion> {
        return this.http.post<Anotacion>(this.apiUrl, anotacion);
    }

    getAll(): Observable<Anotacion[]> {
        return this.http.get<Anotacion[]>(this.apiUrl);
    }

    getById(id: number): Observable<Anotacion> {
        return this.http.get<Anotacion>(`${this.apiUrl}/${id}`);
    }

    update(id: number, anotacion: Partial<Anotacion>): Observable<Anotacion> {
        return this.http.put<Anotacion>(`${this.apiUrl}/${id}`, anotacion);
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}
