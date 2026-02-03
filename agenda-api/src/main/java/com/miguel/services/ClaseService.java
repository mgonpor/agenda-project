package com.miguel.services;

import com.miguel.persistence.entities.Clase;
import com.miguel.persistence.repositories.ClaseRepository;
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

    // --- MÉTODOS DE NEGOCIO (Llamados desde GrupoService o AdminController) ---

    // USER - Se asume validación previa en GrupoService
    public List<ClaseResponse> findByGrupoUser(int idGrupo) {
        return claseRepository.findByIdGrupo(idGrupo).stream()
                .map(ClaseMapper::toDto)
                .toList();
    }

    public ClaseResponse findByIdUser(int idGrupo, int idClase) {
        return claseRepository.findByIdAndIdGrupo(idClase, idGrupo)
                .map(ClaseMapper::toDto)
                .orElseThrow(() -> new ClaseNotFoundException("Clase no encontrada en este grupo"));
    }

    public ClaseResponse createUser(int idGrupo, ClaseRequest claseRequest) {
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

    public ClaseResponse updateUser(int idGrupo, int idClase, ClaseRequest claseRequest, int idUsuario) {
        if (idClase != claseRequest.getId()) {
            throw new ClaseException("El id de clase del path y el body no coinciden");
        }
        if (!this.claseRepository.existsByIdAndIdGrupo(idClase, idGrupo)){
            throw new ClaseNotFoundException("Clase no encontrada");
        }
        validarDatosClase(claseRequest);

        String dia = claseRequest.getDiaSemana().toUpperCase().trim();
        
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

    public void deleteUser(int idGrupo, int idClase) {
        if (!claseRepository.existsByIdAndIdGrupo(idClase, idGrupo)) {
            throw new ClaseNotFoundException("La clase no pertenece a este grupo.");
        }
        claseRepository.deleteById(idClase);
    }
    
    private void validarDatosClase(ClaseRequest claseRequest) {
         if (claseRequest.getTramo() == 0){
            throw new ClaseException("Tramo no incluido.");
        }
        String dia = claseRequest.getDiaSemana().toUpperCase().trim();
        try{
            DayOfWeek.valueOf(dia);
        }catch(IllegalArgumentException e){
            throw new ClaseException("Día no válido.");
        }
    }

    // CRUDs
    // ADMIN
    public List<ClaseResponse> findAllAdmin() {
        if(!isAdmin()) throw new WrongUserException("Usuario no permitido");
        return claseRepository.findAll().stream()
                .map(ClaseMapper::toDto)
                .toList();
    }

    public ClaseResponse findByIdAdmin(int id) {
        if(!isAdmin()) throw new WrongUserException("Usuario no permitido");
        if(!claseRepository.existsById(id)){
            throw new ClaseNotFoundException("Clase no encontrada.");
        }
        return ClaseMapper.toDto(claseRepository.findById(id).get());
    }
    
    public ClaseResponse createAdmin(int idGrupo, ClaseRequest claseRequest) {
        if(!isAdmin()) throw new WrongUserException("Usuario no permitido");
        validarDatosClase(claseRequest);

        claseRequest.setDiaSemana(claseRequest.getDiaSemana().toUpperCase().trim());
        claseRequest.setId(0);
        Clase clase = ClaseMapper.toEntity(claseRequest);
        clase.setIdGrupo(idGrupo);
        // Nota: Si se quisiera validar colisión de horario para el usuario admin, se haría aquí usando idUsuario
        return ClaseMapper.toDto(claseRepository.save(clase));
    }
    
    public ClaseResponse updateAdmin(int idClase, int idGrupo, int idUsuario, ClaseRequest claseRequest){
         if(!isAdmin()) throw new WrongUserException("Usuario no permitido");
         if (idClase != claseRequest.getId()) {
            throw new ClaseException("El id de clase del path y el body no coinciden");
        }
         
         validarDatosClase(claseRequest);
         
         String dia = claseRequest.getDiaSemana().toUpperCase().trim();
        
         Optional<Clase> tramoYDia = claseRepository.findByDiaSemanaAndTramoAndGrupoUsuarioId(
                DayOfWeek.valueOf(dia), claseRequest.getTramo(), idUsuario
         );
         if(tramoYDia.isPresent() && tramoYDia.get().getId() != idClase){
            throw new ClaseException("Ya existe una clase en este día y tramo.");
         }
         
         Clase claseDB = claseRepository.findById(idClase)
             .orElseThrow(() -> new ClaseNotFoundException("Clase no encontrada"));
             
         // Actualizamos
         claseDB.setIdGrupo(idGrupo); // Admin puede mover de grupo
         claseDB.setTramo(claseRequest.getTramo());
         claseDB.setDiaSemana(DayOfWeek.valueOf(dia));
         claseDB.setAula(claseRequest.getAula());
         
         return ClaseMapper.toDto(claseRepository.save(claseDB));
    }

    public void deleteAdmin(int idClase) {
        if(!isAdmin()) {
            throw new WrongUserException("Usuario no permitido");
        }
        if(!claseRepository.existsById(idClase)){
            throw new ClaseNotFoundException("Clase no encontrada.");
        }
        claseRepository.deleteById(idClase);
    }

}
