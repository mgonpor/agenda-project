import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, of, delay } from 'rxjs';
import { AnotacionDto } from '../models/anotacion.model';

@Injectable({
  providedIn: 'root',
})
export class AnotacionService {
  private http = inject(HttpClient);
  private readonly ADMIN_URL = `${environment.apiUrl}/admin/anotacion`;
  private readonly USER_URL = `${environment.apiUrl}/user/anotacion`;

  // --- MOCK DATA ---
  private mockAnotaciones: AnotacionDto[] = [
    { id: 1, fecha: '2026-02-06', texto: 'Reunión de equipo a las 10:00' },
    { id: 2, fecha: '2026-02-07', texto: 'Entrega de proyecto final' },
    { id: 3, fecha: '2026-02-08', texto: 'Estudiar para el examen de redes' },
  ];

  // --- MÉTODOS USER ---
  getAnotacionesByIdGrupoUser(idGrupo: number): Observable<AnotacionDto[]> {
    // return this.http.get<AnotacionDto[]>(`${this.USER_URL}/grupo/${idGrupo}`);
    return of(this.mockAnotaciones).pipe(delay(500));
  }

  getAnotacionByIdUser(idGrupo: number, idAnotacion: number): Observable<AnotacionDto> {
    // return this.http.get<AnotacionDto>(`${this.USER_URL}/grupo/${idGrupo}/${idAnotacion}`);
    const anotacion = this.mockAnotaciones.find(a => a.id === idAnotacion) || this.mockAnotaciones[0];
    return of(anotacion).pipe(delay(500));
  }

  createAnotacionUser(idGrupo: number, anotacion: AnotacionDto): Observable<AnotacionDto> {
    // return this.http.post<AnotacionDto>(`${this.USER_URL}/grupo/${idGrupo}`, anotacion);
    const newAnotacion: AnotacionDto = { ...anotacion, id: Math.random() };
    return of(newAnotacion).pipe(delay(500));
  }

  updateAnotacionUser(idGrupo: number, idAnotacion: number, anotacion: AnotacionDto): Observable<AnotacionDto> {
    // return this.http.put<AnotacionDto>(`${this.USER_URL}/grupo/${idGrupo}/${idAnotacion}`, anotacion);
    const updatedAnotacion: AnotacionDto = { ...anotacion, id: idAnotacion };
    return of(updatedAnotacion).pipe(delay(500));
  }

  deleteAnotacionUser(idGrupo: number, idAnotacion: number): Observable<string> {
    // return this.http.delete(`${this.USER_URL}/grupo/${idGrupo}/${idAnotacion}`, { responseType: 'text' });
    return of('Anotación eliminada (Mock)').pipe(delay(500));
  }

  // --- MÉTODOS ADMIN ---
  getAllAnotacionesAdmin(): Observable<AnotacionDto[]> {
    // return this.http.get<AnotacionDto[]>(this.ADMIN_URL);
    return of(this.mockAnotaciones).pipe(delay(500));
  }

  getAnotacionAdmin(id: number): Observable<AnotacionDto> {
    // return this.http.get<AnotacionDto>(`${this.ADMIN_URL}/${id}`);
    const anotacion = this.mockAnotaciones.find(a => a.id === id) || this.mockAnotaciones[0];
    return of(anotacion).pipe(delay(500));
  }

  createAnotacionAdmin(idGrupo: number, idUsuario: number, anotacion: AnotacionDto): Observable<AnotacionDto> {
    // const params = new HttpParams().set('idGrupo', idGrupo.toString()).set('idUsuario', idUsuario.toString());
    // return this.http.post<AnotacionDto>(this.ADMIN_URL, anotacion, { params });
    const newAnotacion: AnotacionDto = { ...anotacion, id: Math.random() };
    return of(newAnotacion).pipe(delay(500));
  }

  updateAnotacionAdmin(id: number, idGrupo: number, idUsuario: number, anotacion: AnotacionDto): Observable<AnotacionDto> {
    // const params = new HttpParams().set('idGrupo', idGrupo.toString()).set('idUsuario', idUsuario.toString());
    // return this.http.put<AnotacionDto>(`${this.ADMIN_URL}/${id}`, anotacion, { params });
    const updatedAnotacion: AnotacionDto = { ...anotacion, id };
    return of(updatedAnotacion).pipe(delay(500));
  }

  deleteAnotacionAdmin(id: number): Observable<string> {
    // return this.http.delete(`${this.ADMIN_URL}/${id}`, { responseType: 'text' });
    return of('Anotación eliminada por admin (Mock)').pipe(delay(500));
  }
}
