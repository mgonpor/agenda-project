import { AnotacionDto } from '../../anotaciones/models/anotacion.model';

export interface GrupoRequest {
    id: number;     // Se envía 0 o null para creación, ID real para update
    nombre: string;
}

export interface GrupoResponse {
    id: number;
    nombre: string;
    anotaciones: AnotacionDto[];
}