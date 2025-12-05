package com.miguel.services;

import com.miguel.persistence.entities.Anotacion;
import com.miguel.persistence.repositories.AnotacionRepository;
import com.miguel.persistence.repositories.GrupoRepository;
import com.miguel.services.dtos.AnotacionDto;
import com.miguel.services.exceptions.*;
import com.miguel.services.mappers.AnotacionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AnotacionService {

    @Autowired
    private AnotacionRepository anotacionRepository;

    @Autowired
    private GrupoService grupoService;

    public List<AnotacionDto> getAnotaciones(int idGrupo, int idUser){
        if (!grupoService.perteneceAUsuario(idGrupo, idUser)) {
            throw new WrongUserException("El usuario no tiene pertenece a este grupo");
        }
        return anotacionRepository.findByIdGrupo(idGrupo).stream()
                .map(AnotacionMapper::toDto)
                .toList();
    }

    public AnotacionDto getAnotacionById(int idGrupo, int idAnotacion, int idUser){
        if (!grupoService.perteneceAUsuario(idGrupo, idUser)) {
            throw new WrongUserException("El usuario no tiene pertenece a este grupo");
        }
        Optional<Anotacion> a = anotacionRepository.findById(idAnotacion);
        if(a.isEmpty()){
            throw new AnotacionNotFoundException("La anotación no existe");
        }
        if (a.get().getIdGrupo() != idGrupo){
            throw new AnotacionException("La anotación " + idAnotacion + " no pertenece al grupo " + idGrupo);
        }
        return AnotacionMapper.toDto(a.get());
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

    // OTRAS
    public AnotacionDto getAnotacionByGrupoAndFecha(int idGrupo, LocalDate fecha, int idUser){
        if (!grupoService.perteneceAUsuario(idGrupo, idUser)) {
            throw new WrongUserException("El usuario no tiene pertenece a este grupo");
        }
        Optional<Anotacion> a = anotacionRepository.findByIdGrupoAndFecha(idGrupo, fecha);
        if(a.isEmpty()){
            throw new AnotacionNotFoundException("La anotación de este día para este grupo no existe");
        }
        return AnotacionMapper.toDto(a.get());
    }

    public AnotacionDto cambiarTexto(int idGrupo, int idAnotacion, String texto, int idUser){
        if (!grupoService.perteneceAUsuario(idGrupo, idUser)) {
            throw new WrongUserException("El usuario no tiene pertenece a este grupo");
        }
        if(!anotacionRepository.existsById(idAnotacion)){
            throw new AnotacionNotFoundException("Anotacion no encontrada.");
        }
        Anotacion anotacionDB = anotacionRepository.findById(idAnotacion).get();
        if(idGrupo != anotacionDB.getIdGrupo()){
            throw new AnotacionException("Parece que esta anotacion no pertenece al grupo indicado en el path");
        }
        anotacionDB.setTexto(texto);
        return AnotacionMapper.toDto(anotacionRepository.save(anotacionDB));
    }

}
