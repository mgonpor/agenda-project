package com.miguel.services;

import com.miguel.persistence.entities.Anotacion;
import com.miguel.persistence.repositories.AnotacionRepository;
import com.miguel.services.dtos.AnotacionDto;
import com.miguel.services.exceptions.*;
import com.miguel.services.mappers.AnotacionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnotacionService {

    @Autowired
    private AnotacionRepository anotacionRepository;

    public List<AnotacionDto> findByGrupo(int idGrupo){
        return anotacionRepository.findByIdGrupo(idGrupo).stream()
                .map(AnotacionMapper::toDto)
                .toList();
    }

    public AnotacionDto findById(int idGrupo, int idAnotacion){
        if (!this.anotacionRepository.existsByIdAndIdGrupo(idAnotacion, idGrupo)){
            throw new AnotacionNotFoundException("Anotacion no encontrada");
        }
        return AnotacionMapper.toDto(anotacionRepository.findByIdAndIdGrupo().get());
    }

    public AnotacionDto createAnotacion(int idGrupo, AnotacionDto anotacionRequest, int idUser){
        if (!grupoService.perteneceAUsuario(idGrupo, idUser)) {
            throw new WrongUserException("El usuario no tiene pertenece a este grupo");
        }
        if(anotacionRequest.getFecha() == null){
            throw new AnotacionException("Indique una fecha válida.");
        }
        if (anotacionRepository.findByIdGrupoAndFecha(idGrupo, anotacionRequest.getFecha()).isPresent()){
            throw new AnotacionException("Esta anotación ya existe (idGrupo y fecha).");
        }
        if (anotacionRequest.getTexto().isBlank()){
            throw new EmptyTextException("No se puede guardar una anotación vacía.");
        }

        Anotacion a = AnotacionMapper.toEntity(anotacionRequest);
        a.setId(0);
        a.setIdGrupo(idGrupo);

        anotacionRepository.save(a);

        return AnotacionMapper.toDto(a);
    }

    public AnotacionDto updateAnotacion(int idGrupo, int idAnotacion, AnotacionDto anotacionRequest, int idUser){
        if (!grupoService.perteneceAUsuario(idGrupo, idUser)) {
            throw new WrongUserException("El usuario no tiene pertenece a este grupo");
        }
        if (idAnotacion != anotacionRequest.getId()){
            throw new AnotacionException("Los id del path y el body no coinciden.");
        }
        if (!anotacionRepository.existsById(idAnotacion)){
            throw new AnotacionNotFoundException("Anotación no encontrada.");
        }
        if(anotacionRequest.getFecha() == null){
            throw new AnotacionException("Indique una fecha válida.");
        }
        if (anotacionRequest.getTexto().isBlank()){
            throw new EmptyTextException("No se puede guardar una anotación vacía.");
        }

        Anotacion a = AnotacionMapper.toEntity(anotacionRequest);
        a.setIdGrupo(idGrupo);

        anotacionRepository.save(a);
        return AnotacionMapper.toDto(a);
    }

    public void delete(int idGrupo, int idAnotacion, int idUser){
        if (!grupoService.perteneceAUsuario(idGrupo, idUser)) {
            throw new WrongUserException("El usuario no tiene pertenece a este grupo");
        }
        if (!anotacionRepository.existsByIdAndIdGrupo(idAnotacion, idGrupo)){
            throw new AnotacionNotFoundException("Anotacion no encontrada.");
        }
        anotacionRepository.deleteById(idAnotacion);
    }
}
