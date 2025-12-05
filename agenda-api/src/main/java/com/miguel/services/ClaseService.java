package com.miguel.services;

import com.miguel.persistence.entities.Clase;
import com.miguel.persistence.repositories.ClaseRepository;
import com.miguel.security.user.Role;
import com.miguel.security.user.User;
import com.miguel.services.dtos.ClaseRequest;
import com.miguel.services.dtos.ClaseResponse;
import com.miguel.services.exceptions.ClaseException;
import com.miguel.services.exceptions.ClaseNotFoundException;
import com.miguel.services.exceptions.WrongUserException;
import com.miguel.services.mappers.ClaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Service
public class ClaseService {

    @Autowired
    private ClaseRepository claseRepository;


    @Autowired
    private GrupoService grupoService;

    // CRUDs
    // ADMIN
    public List<ClaseResponse> findAll(User user) {
        if(user.getRole() != Role.ADMIN) {
            throw new WrongUserException("Usuario no permitido");
        }
        return claseRepository.findAll().stream()
                .map(ClaseMapper::toDto)
                .toList();
    }

    // ADMIN
    public ClaseResponse findById(int id, User user) {
        if(user.getRole() != Role.ADMIN) {
            throw new WrongUserException("Usuario no permitido");
        }
        if(!claseRepository.existsById(id)){
            throw new ClaseNotFoundException("Clase no encontrada.");
        }
        return ClaseMapper.toDto(claseRepository.findById(id).get());
    }

    public ClaseResponse create(int idGrupo, ClaseRequest claseRequest, int idUser) {
        if (!grupoService.perteneceAUsuario(idGrupo, idUser)) {
            throw new WrongUserException("El grupo no pertenece a este usuario");
        }
        if(idGrupo != claseRequest.getIdGrupo()) {
            throw new ClaseException("El id del grupo del path y el body no coinciden");
        }
        if (claseRequest.getTramo() == 0){
            throw new ClaseException("Tramo no incluido.");
        }
        String dia = claseRequest.getDiaSemana().toUpperCase().trim();
        try{
            DayOfWeek.valueOf(dia);
        }catch(IllegalArgumentException e){
            throw new ClaseException("Día no válido.");
        }

        claseRequest.setDiaSemana(dia);
        claseRequest.setId(0);

        return ClaseMapper.toDto(claseRepository.save(ClaseMapper.toEntity(claseRequest)));
    }

    public ClaseResponse update(int idGrupo, int id, ClaseRequest claseRequest, int idUser) {
        if (!grupoService.perteneceAUsuario(idGrupo, idUser)) {
            throw new WrongUserException("El grupo no pertenece a este usuario");
        }
        if(idGrupo != claseRequest.getIdGrupo()) {
            throw new ClaseException("El id del grupo del path y el body no coinciden");
        }
        if(id != claseRequest.getId()){
            throw new ClaseException("El id del path y el body no coinciden.");
        }
        if (!this.perteneceAGrupo(id, idGrupo)){
            throw new ClaseException("La clase no pertenece a este grupo");
        }
        if (claseRequest.getTramo() == 0){
            throw new ClaseException("Tramo no incluido.");
        }
        String dia = claseRequest.getDiaSemana().toUpperCase().trim();
        try{
            DayOfWeek.valueOf(dia);
        }catch(IllegalArgumentException e){
            throw new ClaseException("Día no válido.");
        }
        Optional<Clase> tramoYDia = claseRepository.findByDiaSemanaAndTramo(DayOfWeek.valueOf(dia), claseRequest.getTramo());
        if(tramoYDia.isPresent() && tramoYDia.get().getId() != id){
            throw new ClaseException("Ya existe una clase en este día y tramo.");
        }

        Clase claseDB = claseRepository.findById(id).get();
        claseDB.setIdGrupo(claseRequest.getIdGrupo());
        claseDB.setTramo(claseRequest.getTramo());
        claseDB.setDiaSemana(DayOfWeek.valueOf(dia));
        claseDB.setAula(claseRequest.getAula());

        return ClaseMapper.toDto(claseRepository.save(claseDB));
    }

    public void delete(int idGrupo, int id, int idUser) {
        if (!grupoService.perteneceAUsuario(idGrupo, idUser)) {
            throw new WrongUserException("El grupo no pertenece a este usuario");
        }
        if (!this.perteneceAGrupo(id, idGrupo)) {
            throw new ClaseException("La clase no pertenece a este grupo.");
        }
        claseRepository.deleteById(id);
    }

    public ClaseResponse cambiarAula(int idGrupo, int id, String aula, int idUser) {
        if (!grupoService.perteneceAUsuario(idGrupo, idUser)) {
            throw new WrongUserException("El grupo no pertenece a este usuario");
        }
        if (!this.perteneceAGrupo(id, idGrupo)) {
            throw new ClaseException("La clase no pertenece a este grupo.");
        }
        Clase claseDB = claseRepository.findById(id).get();
        claseDB.setAula(aula);
        return ClaseMapper.toDto(claseRepository.save(claseDB));
    }

    public List<ClaseResponse> findByGrupo(int idGrupo, int idUser){
        if (!grupoService.perteneceAUsuario(idGrupo, idUser)) {
            throw new WrongUserException("El grupo no pertenece a este usuario");
        }
        return claseRepository.findByIdGrupo(idGrupo).stream()
                .map(ClaseMapper::toDto)
                .toList();
    }

    public boolean perteneceAGrupo(int idClase, int idGrupo){
        return claseRepository.existsByIdAndIdGrupo(idClase, idGrupo);
    }
}
