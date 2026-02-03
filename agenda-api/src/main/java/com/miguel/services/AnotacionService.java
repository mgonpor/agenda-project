package com.miguel.services;

import com.miguel.persistence.entities.Anotacion;
import com.miguel.persistence.repositories.AnotacionRepository;
import com.miguel.persistence.entities.Usuario;
import com.miguel.services.dtos.AnotacionDto;
import com.miguel.services.exceptions.AnotacionException;
import com.miguel.services.exceptions.AnotacionNotFoundException;
import com.miguel.services.exceptions.EmptyTextException;
import com.miguel.services.exceptions.WrongUserException;
import com.miguel.services.mappers.AnotacionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnotacionService {

    @Autowired
    private AnotacionRepository anotacionRepository;

    // --- MÉTODOS AUXILIARES ---

    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    // ==========================================
    // SECCIÓN USER (Llamados desde GrupoService)
    // ==========================================

    public List<AnotacionDto> findByGrupo(int idGrupo){
        // Seguridad garantizada por GrupoService.validarPropiedadGrupo
        return anotacionRepository.findByIdGrupo(idGrupo).stream()
                .map(AnotacionMapper::toDto)
                .toList();
    }

    public AnotacionDto findById(int idGrupo, int idAnotacion){
        return anotacionRepository.findByIdAndIdGrupo(idAnotacion, idGrupo)
                .map(AnotacionMapper::toDto)
                .orElseThrow(() -> new AnotacionNotFoundException("Anotación no encontrada en este grupo"));
    }

    public AnotacionDto createAnotacion(int idGrupo, AnotacionDto anotacionRequest){
        validarDatosAnotacion(idGrupo, anotacionRequest, true);

        Anotacion a = AnotacionMapper.toEntity(anotacionRequest);
        a.setId(0);
        a.setIdGrupo(idGrupo);

        return AnotacionMapper.toDto(anotacionRepository.save(a));
    }

    public AnotacionDto updateAnotacion(int idGrupo, int idAnotacion, AnotacionDto anotacionRequest){
        if (idAnotacion != anotacionRequest.getId()){
            throw new AnotacionException("Los id del path y el body no coinciden.");
        }
        if (!anotacionRepository.existsByIdAndIdGrupo(idAnotacion, idGrupo)){
            throw new AnotacionNotFoundException("Anotación no encontrada en este grupo");
        }

        validarDatosAnotacion(idGrupo, anotacionRequest, false);

        Anotacion a = AnotacionMapper.toEntity(anotacionRequest);
        a.setIdGrupo(idGrupo);

        return AnotacionMapper.toDto(anotacionRepository.save(a));
    }

    public void delete(int idGrupo, int idAnotacion){
        if (!anotacionRepository.existsByIdAndIdGrupo(idAnotacion, idGrupo)){
            throw new AnotacionNotFoundException("Anotación no encontrada en este grupo.");
        }
        anotacionRepository.deleteById(idAnotacion);
    }

    // ==========================================
    // SECCIÓN ADMIN (Acceso Directo)
    // ==========================================

    public List<AnotacionDto> findAllAdmin(){
        if(!isAdmin()) throw new WrongUserException("Acceso denegado");

        return this.anotacionRepository.findAll().stream()
                .map(AnotacionMapper::toDto)
                .toList();
    }

    public AnotacionDto findByIdAdmin(int id){
        if(!isAdmin()) throw new WrongUserException("Acceso denegado");

        return anotacionRepository.findById(id)
                .map(AnotacionMapper::toDto)
                .orElseThrow(() -> new AnotacionNotFoundException("Anotación no encontrada."));
    }

    public void deleteAdmin(int id){
        if(!isAdmin()) throw new WrongUserException("Acceso denegado");

        if(!anotacionRepository.existsById(id)){
            throw new AnotacionNotFoundException("Anotación no encontrada.");
        }
        this.anotacionRepository.deleteById(id);
    }

    // --- VALIDACIONES DE NEGOCIO REUTILIZABLES ---

    private void validarDatosAnotacion(int idGrupo, AnotacionDto dto, boolean esNuevo) {
        if(dto.getFecha() == null){
            throw new AnotacionException("Indique una fecha válida.");
        }
        if (dto.getTexto() == null || dto.getTexto().isBlank()){
            throw new EmptyTextException("No se puede guardar una anotación vacía.");
        }
        // Solo validamos duplicados por fecha si es una anotación nueva
        if (esNuevo && anotacionRepository.findByIdGrupoAndFecha(idGrupo, dto.getFecha()).isPresent()){
            throw new AnotacionException("Ya existe una anotación para esta fecha en este grupo.");
        }
    }
}