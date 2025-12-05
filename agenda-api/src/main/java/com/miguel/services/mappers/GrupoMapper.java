package com.miguel.services.mappers;

import com.miguel.persistence.entities.Anotacion;
import com.miguel.persistence.entities.Grupo;
import com.miguel.services.dtos.AnotacionDto;
import com.miguel.services.dtos.GrupoRequest;
import com.miguel.services.dtos.GrupoResponse;

import java.util.ArrayList;
import java.util.List;

public class GrupoMapper {

    public static GrupoResponse toDto(Grupo grupo) {
        GrupoResponse dto = new GrupoResponse();

        dto.setId(grupo.getId());
        dto.setNombre(grupo.getNombre());

        List<AnotacionDto> anotaciones = new ArrayList<AnotacionDto>();

        if (grupo.getAnotaciones() != null) {
            for(Anotacion anotacion : grupo.getAnotaciones()) {
                anotaciones.add(AnotacionMapper.toDto(anotacion));
            }
            dto.setAnotaciones(anotaciones);
        }else {
            dto.setAnotaciones(List.of());
        }

        return dto;
    }

    public static Grupo toEntity(GrupoRequest grupoRequest) {
        Grupo entity = new Grupo();

        entity.setId(grupoRequest.getId());
        entity.setNombre(grupoRequest.getNombre());

        return entity;
    }

}
