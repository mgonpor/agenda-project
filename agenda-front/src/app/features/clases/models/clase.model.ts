export interface ClaseResponse {
    id: number;
    idGrupo: number; // Relación con el grupo
    grupo: string;   // Nombre del grupo (para mostrar en UI)
    diaSemana: string;
    tramo: number;
    aula: string;
}