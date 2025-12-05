package com.miguel.services.mappers;

import com.miguel.persistence.entities.Anotacion;
import com.miguel.services.dtos.AnotacionDto;

public class AnotacionMapper {

    public static AnotacionDto toDto(Anotacion anotacion) {
        AnotacionDto dto = new AnotacionDto();

        dto.setId(anotacion.getId());
        dto.setFecha(anotacion.getFecha());
        dto.setTexto(anotacion.getTexto());

        return dto;
    }

    public static Anotacion toEntity(AnotacionDto dto) {
        Anotacion entity = new Anotacion();

        entity.setId(dto.getId());
        entity.setFecha(dto.getFecha());
        entity.setTexto(dto.getTexto());

        return entity;
    }

}
