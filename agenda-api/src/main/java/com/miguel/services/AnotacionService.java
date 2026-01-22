package com.miguel.services;

import com.miguel.persistence.entities.Anotacion;
import com.miguel.persistence.repositories.AnotacionRepository;
import com.miguel.security.user.Role;
import com.miguel.security.user.User;
import com.miguel.services.dtos.AnotacionDto;
import com.miguel.services.exceptions.AnotacionException;
import com.miguel.services.exceptions.AnotacionNotFoundException;
import com.miguel.services.exceptions.EmptyTextException;
import com.miguel.services.exceptions.WrongUserException;
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
        return AnotacionMapper.toDto(anotacionRepository.findByIdAndIdGrupo(idAnotacion, idGrupo).get());
    }

    public AnotacionDto createAnotacion(int idGrupo, AnotacionDto anotacionRequest){
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

        return AnotacionMapper.toDto(anotacionRepository.save(a));
    }

    public AnotacionDto updateAnotacion(int idGrupo, int idAnotacion, AnotacionDto anotacionRequest){
        if (idAnotacion != anotacionRequest.getId()){
            throw new AnotacionException("Los id del path y el body no coinciden.");
        }
        if (!this.anotacionRepository.existsByIdAndIdGrupo(idAnotacion, idGrupo)){
            throw new AnotacionNotFoundException("Anotacion no encontrada");
        }
        if(anotacionRequest.getFecha() == null){
            throw new AnotacionException("Indique una fecha válida.");
        }
        if (anotacionRequest.getTexto().isBlank()){
            throw new EmptyTextException("No se puede guardar una anotación vacía.");
        }

        Anotacion a = AnotacionMapper.toEntity(anotacionRequest);
        a.setIdGrupo(idGrupo);

        return AnotacionMapper.toDto(anotacionRepository.save(a));
    }

    public void delete(int idGrupo, int idAnotacion){
        if (!anotacionRepository.existsByIdAndIdGrupo(idAnotacion, idGrupo)){
            throw new AnotacionNotFoundException("Anotacion no encontrada.");
        }
        anotacionRepository.deleteById(idAnotacion);
    }

    // ADMIN
    public List<AnotacionDto> findAll(User user){
        if(user.getRole() != Role.ADMIN) {
            throw new WrongUserException("Usuario no permitido");
        }
        return this.anotacionRepository.findAll().stream()
                .map(AnotacionMapper::toDto)
                .toList();
    }

    public AnotacionDto findByIdAdmin(int id, User user){
        if(user.getRole() != Role.ADMIN) {
            throw new WrongUserException("Usuario no permitido");
        }
        if(!anotacionRepository.existsById(id)){
            throw new AnotacionNotFoundException("Anotacion no encontrada.");
        }
        return AnotacionMapper.toDto(this.anotacionRepository.findById(id).get());
    }

    //Reutilizamos create y update tras pasar por grupoService

    public void deleteAdmin(int id, User user){
        if(user.getRole() != Role.ADMIN) {
            throw new WrongUserException("Usuario no permitido");
        }
        if(!anotacionRepository.existsById(id)){
            throw new AnotacionNotFoundException("Anotacion no encontrada.");
        }
        this.anotacionRepository.deleteById(id);
    }
}
