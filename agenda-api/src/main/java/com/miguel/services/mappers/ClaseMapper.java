package com.miguel.services.mappers;

import com.miguel.persistence.entities.Clase;
import com.miguel.services.dtos.ClaseRequest;
import com.miguel.services.dtos.ClaseResponse;

import java.time.DayOfWeek;

public class ClaseMapper {

    public static ClaseResponse toDto(Clase clase) {
        ClaseResponse dto = new ClaseResponse();

        dto.setId(clase.getId());
        dto.setIdGrupo(clase.getIdGrupo());
        if(clase.getGrupo()!=null){
            dto.setGrupo(clase.getGrupo().getNombre());
        }else {
            dto.setGrupo("");
        }
        dto.setDiaSemana(clase.getDiaSemana().toString());
        dto.setTramo(clase.getTramo());
        dto.setAula(clase.getAula());

        return dto;
    }

    public static Clase toEntity(ClaseRequest dto) {
        Clase entity = new Clase();

        entity.setId(dto.getId());

        entity.setDiaSemana(DayOfWeek.valueOf(dto.getDiaSemana()));

        entity.setTramo(dto.getTramo());
        entity.setAula(dto.getAula());

        return entity;
    }

}
