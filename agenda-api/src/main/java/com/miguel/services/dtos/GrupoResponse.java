package com.miguel.services.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GrupoResponse {

    private int id;
    private String nombre;
    private List<AnotacionDto> anotaciones;

}
