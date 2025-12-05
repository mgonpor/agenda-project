package com.miguel.services.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClaseResponse {

    private int id;
    private int idGrupo;
    private String grupo;
    private String diaSemana;
    private int tramo;
    private String aula;

}
