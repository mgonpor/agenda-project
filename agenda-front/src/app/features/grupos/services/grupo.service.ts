import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { Observable, of, delay } from 'rxjs';
import { GrupoRequest, GrupoResponse } from '../models/grupo.model';

@Injectable({
  providedIn: 'root',
})
export class GrupoService {
  private http = inject(HttpClient);
  private readonly ADMIN_URL = `${environment.apiUrl}/admin/grupo`;
  private readonly USER_URL = `${environment.apiUrl}/user/grupo`;

  // --- MOCK DATA ---
  private mockGrupos: GrupoResponse[] = [
    { id: 1, nombre: 'Desarrollo Web', anotaciones: [] },
    { id: 2, nombre: 'Sistemas', anotaciones: [] },
    { id: 3, nombre: 'Diseño Interfaz', anotaciones: [] },
  ];

  // --- MÉTODOS USER (Tu UserGrupoController) ---
  getGruposUser(): Observable<GrupoResponse[]> {
    // return this.http.get<GrupoResponse[]>(this.USER_URL);
    return of(this.mockGrupos).pipe(delay(500));
  }

  getGrupoUser(id: number): Observable<GrupoResponse> {
    // return this.http.get<GrupoResponse>(`${this.USER_URL}/${id}`);
    const grupo = this.mockGrupos.find(g => g.id === id) || this.mockGrupos[0];
    return of(grupo).pipe(delay(500));
  }

  createGrupoUser(grupo: GrupoRequest): Observable<GrupoResponse> {
    // return this.http.post<GrupoResponse>(this.USER_URL, grupo);
    const newGrupo: GrupoResponse = { ...grupo, id: Math.floor(Math.random() * 1000), anotaciones: [] };
    return of(newGrupo).pipe(delay(500));
  }

  updateGrupoUser(id: number, grupo: GrupoRequest): Observable<GrupoResponse> {
    // return this.http.put<GrupoResponse>(`${this.USER_URL}/${id}`, grupo);
    const updatedGrupo: GrupoResponse = { ...grupo, id, anotaciones: [] };
    return of(updatedGrupo).pipe(delay(500));
  }

  deleteGrupoUser(id: number): Observable<string> {
    // return this.http.delete(`${this.USER_URL}/${id}`, { responseType: 'text' });
    return of('Grupo eliminado correctamente (Mock)').pipe(delay(500));
  }

  // --- MÉTODOS ADMIN (Tu AdminGrupoController) ---
  getAllGruposAdmin(): Observable<GrupoResponse[]> {
    // return this.http.get<GrupoResponse[]>(this.ADMIN_URL);
    return of(this.mockGrupos).pipe(delay(500));
  }

  getGrupoAdmin(id: number): Observable<GrupoResponse> {
    // return this.http.get<GrupoResponse>(`${this.ADMIN_URL}/${id}`);
    const grupo = this.mockGrupos.find(g => g.id === id) || this.mockGrupos[0];
    return of(grupo).pipe(delay(500));
  }

  createGrupoAdmin(grupo: GrupoRequest, idUsuario: number): Observable<GrupoResponse> {
    // const params = new HttpParams().set('idUsuario', idUsuario.toString());
    // return this.http.post<GrupoResponse>(this.ADMIN_URL, grupo, { params });
    const newGrupo: GrupoResponse = { ...grupo, id: Math.floor(Math.random() * 1000), anotaciones: [] };
    return of(newGrupo).pipe(delay(500));
  }

  updateGrupoAdmin(id: number, grupo: GrupoRequest, idUsuario: number): Observable<GrupoResponse> {
    // const params = new HttpParams().set('idUsuario', idUsuario.toString());
    // return this.http.put<GrupoResponse>(`${this.ADMIN_URL}/${id}`, grupo, { params });
    const updatedGrupo: GrupoResponse = { ...grupo, id, anotaciones: [] };
    return of(updatedGrupo).pipe(delay(500));
  }

  deleteGrupoAdmin(id: number): Observable<string> {
    // return this.http.delete(`${this.ADMIN_URL}/${id}`, { responseType: 'text' });
    return of('Grupo eliminado por admin (Mock)').pipe(delay(500));
  }
}
