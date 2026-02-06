export interface AnotacionDto {
    id: number;
    fecha: string; // En TS manejamos el LocalDate de Java como string ISO (YYYY-MM-DD)
    texto: string;
}