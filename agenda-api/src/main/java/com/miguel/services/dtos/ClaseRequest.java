package com.miguel.services.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClaseRequest {

    private int id;
    private String diaSemana;
    private int tramo;
    private String aula;

}
