import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { ClaseRequest, ClaseResponse } from '../models/clase.model';
import { Observable, of, delay } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ClaseService {
  private http = inject(HttpClient);
  private readonly ADMIN_URL = `${environment.apiUrl}/admin/clase`;
  private readonly USER_URL = `${environment.apiUrl}/user/clase`;

  // --- MOCK DATA ---
  private mockClases: ClaseResponse[] = [
    { id: 1, idGrupo: 1, grupo: 'Desarrollo Web', diaSemana: 'LUNES', tramo: 1, aula: 'Aula 101' },
    { id: 2, idGrupo: 1, grupo: 'Desarrollo Web', diaSemana: 'MARTES', tramo: 2, aula: 'Aula 102' },
    { id: 3, idGrupo: 2, grupo: 'Sistemas', diaSemana: 'MIERCOLES', tramo: 3, aula: 'Aula 201' },
  ];

  // --- MÉTODOS USER ---
  getClasesByIdGrupoUser(idGrupo: number): Observable<ClaseResponse[]> {
    // return this.http.get<ClaseResponse[]>(`${this.USER_URL}/grupo/${idGrupo}`);
    const clases = this.mockClases.filter(c => c.idGrupo === idGrupo);
    return of(clases).pipe(delay(500));
  }

  getClaseByIdUser(idGrupo: number, idClase: number): Observable<ClaseResponse> {
    // return this.http.get<ClaseResponse>(`${this.USER_URL}/grupo/${idGrupo}/${idClase}`);
    const clase = this.mockClases.find(c => c.id === idClase) || this.mockClases[0];
    return of(clase).pipe(delay(500));
  }

  createClaseUser(idGrupo: number, clase: ClaseRequest): Observable<ClaseResponse> {
    // return this.http.post<ClaseResponse>(`${this.USER_URL}/grupo/${idGrupo}`, clase);
    const newClase: ClaseResponse = { ...clase, id: Math.random(), idGrupo, grupo: 'Mock Grupo' };
    return of(newClase).pipe(delay(500));
  }

  updateClaseUser(idGrupo: number, idClase: number, clase: ClaseRequest): Observable<ClaseResponse> {
    // return this.http.put<ClaseResponse>(`${this.USER_URL}/grupo/${idGrupo}/${idClase}`, clase);
    const updatedClase: ClaseResponse = { ...clase, id: idClase, idGrupo, grupo: 'Mock Grupo' };
    return of(updatedClase).pipe(delay(500));
  }

  deleteClaseUser(idGrupo: number, idClase: number): Observable<string> {
    // return this.http.delete(`${this.USER_URL}/grupo/${idGrupo}/${idClase}`, { responseType: 'text' });
    return of('Clase eliminada (Mock)').pipe(delay(500));
  }

  // --- MÉTODOS ADMIN ---
  getAllClasesAdmin(): Observable<ClaseResponse[]> {
    // return this.http.get<ClaseResponse[]>(this.ADMIN_URL);
    return of(this.mockClases).pipe(delay(500));
  }

  getClaseAdmin(id: number): Observable<ClaseResponse> {
    // return this.http.get<ClaseResponse>(`${this.ADMIN_URL}/${id}`);
    const clase = this.mockClases.find(c => c.id === id) || this.mockClases[0];
    return of(clase).pipe(delay(500));
  }

  createClaseAdmin(idGrupo: number, idUsuario: number, clase: ClaseRequest): Observable<ClaseResponse> {
    // const params = new HttpParams().set('idGrupo', idGrupo.toString()).set('idUsuario', idUsuario.toString());
    // return this.http.post<ClaseResponse>(this.ADMIN_URL, clase, { params });
    const newClase: ClaseResponse = { ...clase, id: Math.random(), idGrupo, grupo: 'Mock Admin Grupo' };
    return of(newClase).pipe(delay(500));
  }

  updateClaseAdmin(id: number, idGrupo: number, idUsuario: number, clase: ClaseRequest): Observable<ClaseResponse> {
    // const params = new HttpParams().set('idGrupo', idGrupo.toString()).set('idUsuario', idUsuario.toString());
    // return this.http.put<ClaseResponse>(`${this.ADMIN_URL}/${id}`, clase, { params });
    const updatedClase: ClaseResponse = { ...clase, id, idGrupo, grupo: 'Mock Admin Grupo' };
    return of(updatedClase).pipe(delay(500));
  }

  deleteClaseAdmin(id: number): Observable<string> {
    // return this.http.delete(`${this.ADMIN_URL}/${id}`, { responseType: 'text' });
    return of('Clase eliminada por admin (Mock)').pipe(delay(500));
  }
}
