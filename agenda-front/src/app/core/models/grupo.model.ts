import { Clase } from './clase.model';
import { Anotacion } from './anotacion.model';

export interface Grupo {
    id: number;
    nombre: string;
    clases?: Clase[];
    anotaciones?: Anotacion[];
}
