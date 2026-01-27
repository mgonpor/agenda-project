export interface Clase {
    id: number;
    idGrupo: number;
    weekDay: number | string;
    tramo: string; // "08:00-09:00" or "1st", "2nd", etc.
    aula: string;
}
