import { AnotacionDto } from '../../anotaciones/models/anotacion.model';

export interface GrupoResponse {
    id: number;
    nombre: string;
    anotaciones: AnotacionDto[];
}