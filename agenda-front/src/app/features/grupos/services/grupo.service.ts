import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { Observable } from 'rxjs';
import { GrupoRequest, GrupoResponse } from '../models/grupo.model';

@Injectable({
  providedIn: 'root',
})
export class GrupoService {
  private http = inject(HttpClient);
  private readonly ADMIN_URL = `${environment.apiUrl}/admin/grupo`;
  private readonly USER_URL = `${environment.apiUrl}/user/grupo`;

  // --- MÉTODOS USER (Tu UserGrupoController) ---
  getGruposUser(): Observable<GrupoResponse[]> {
    return this.http.get<GrupoResponse[]>(this.USER_URL);
  }

  getGrupoUser(id: number): Observable<GrupoResponse> {
    return this.http.get<GrupoResponse>(`${this.USER_URL}/${id}`);
  }

  createGrupoUser(grupo: GrupoRequest): Observable<any> {
    return this.http.post(this.USER_URL, grupo);
  }

  updateGrupoUser(id: number, grupo: GrupoRequest): Observable<any> {
    return this.http.put(`${this.USER_URL}/${id}`, grupo);
  }

  deleteGrupoUser(id: number): Observable<any> {
    return this.http.delete(`${this.USER_URL}/${id}`);
  }

  // --- MÉTODOS ADMIN (Tu AdminGrupoController) ---
  // Nota: Usamos HttpParams para el @RequestParam idUsuario de tu Java
  getAllGruposAdmin(): Observable<GrupoResponse[]> {
    return this.http.get<GrupoResponse[]>(this.ADMIN_URL);
  }

  getGrupoAdmin(id: number): Observable<GrupoResponse> {
    return this.http.get<GrupoResponse>(`${this.ADMIN_URL}/${id}`);
  }

  createGrupoAdmin(grupo: GrupoRequest, idUsuario: number): Observable<any> {
    const params = new HttpParams().set('idUsuario', idUsuario.toString());
    return this.http.post(this.ADMIN_URL, grupo, { params });
  }

  updateGrupoAdmin(id: number, grupo: GrupoRequest, idUsuario: number): Observable<any> {
    const params = new HttpParams().set('idUsuario', idUsuario.toString());
    return this.http.put(`${this.ADMIN_URL}/${id}`, grupo, { params });
  }

  deleteGrupoAdmin(id: number): Observable<string> {
    // El backend devuelve un String plano, configuramos responseType
    return this.http.delete(`${this.ADMIN_URL}/${id}`, { responseType: 'text' });
  }
}
