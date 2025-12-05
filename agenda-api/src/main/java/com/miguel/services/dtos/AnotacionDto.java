package com.miguel.services.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AnotacionDto {

    private int id;
    private LocalDate fecha;
    private String texto;

}
