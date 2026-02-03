package com.miguel.services;

import com.miguel.persistence.entities.Clase;
import com.miguel.persistence.repositories.ClaseRepository;
import com.miguel.persistence.entities.Usuario;
import com.miguel.services.dtos.ClaseRequest;
import com.miguel.services.dtos.ClaseResponse;
import com.miguel.services.exceptions.ClaseException;
import com.miguel.services.exceptions.ClaseNotFoundException;
import com.miguel.services.exceptions.WrongUserException;
import com.miguel.services.mappers.ClaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Service
public class ClaseService {
    @Autowired
    private ClaseRepository claseRepository;

    // --- MÉTODOS AUXILIARES ---

    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    // --- MÉTODOS DE NEGOCIO (Llamados desde GrupoService) ---

    public List<ClaseResponse> findByGrupo(int idGrupo) {
        // La seguridad la garantiza GrupoService.validarAccesoGrupo
        return claseRepository.findByIdGrupo(idGrupo).stream()
                .map(ClaseMapper::toDto)
                .toList();
    }

    public ClaseResponse findById(int idGrupo, int idClase) {
        return claseRepository.findByIdAndIdGrupo(idClase, idGrupo)
                .map(ClaseMapper::toDto)
                .orElseThrow(() -> new ClaseNotFoundException("Clase no encontrada en este grupo"));
    }

    public ClaseResponse create(int idGrupo, ClaseRequest claseRequest) {
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
        Clase clase = ClaseMapper.toEntity(claseRequest);
        clase.setIdGrupo(idGrupo);

        return ClaseMapper.toDto(claseRepository.save(clase));
    }

    public ClaseResponse update(int idGrupo, int idClase, ClaseRequest claseRequest, int idUsuario) {
        if (idClase != claseRequest.getId()) {
            throw new ClaseException("El id de clase del path y el body no coinciden");
        }
        if (!this.claseRepository.existsByIdAndIdGrupo(idClase, idGrupo)){
            throw new ClaseNotFoundException("Clase no encontrada");
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
        Optional<Clase> tramoYDia = claseRepository.findByDiaSemanaAndTramoAndGrupoUsuarioId(
                DayOfWeek.valueOf(dia), claseRequest.getTramo(), idUsuario
        );
        if(tramoYDia.isPresent() && tramoYDia.get().getId() != idClase){
            throw new ClaseException("Ya existe una clase en este día y tramo.");
        }

        Clase claseDB = claseRepository.findById(idClase).get();
        claseDB.setTramo(claseRequest.getTramo());
        claseDB.setDiaSemana(DayOfWeek.valueOf(dia));
        claseDB.setAula(claseRequest.getAula());

        return ClaseMapper.toDto(claseRepository.save(claseDB));
    }

    public void delete(int idGrupo, int idClase) {
        if (!claseRepository.existsByIdAndIdGrupo(idClase, idGrupo)) {
            throw new ClaseNotFoundException("La clase no pertenece a este grupo.");
        }
        claseRepository.deleteById(idClase);
    }

    // CRUDs
    // ADMIN
    public List<ClaseResponse> findAll(Usuario usuario) {
        if(usuario.getRole() != Role.ADMIN) {
            throw new WrongUserException("Usuario no permitido");
        }
        return claseRepository.findAll().stream()
                .map(ClaseMapper::toDto)
                .toList();
    }

    public ClaseResponse findById(int id, Usuario usuario) {
        if(usuario.getRole() != Role.ADMIN) {
            throw new WrongUserException("Usuario no permitido");
        }
        if(!claseRepository.existsById(id)){
            throw new ClaseNotFoundException("Clase no encontrada.");
        }
        return ClaseMapper.toDto(claseRepository.findById(id).get());
    }

    // Reutilizamos create y update tras pasar por grupo service

    public void deleteAdmin(int idClase, Usuario usuario) {
        if(usuario.getRole() != Role.ADMIN) {
            throw new WrongUserException("Usuario no permitido");
        }
        if(!claseRepository.existsById(idClase)){
            throw new ClaseNotFoundException("Clase no encontrada.");
        }
        claseRepository.deleteById(idClase);
    }

}
