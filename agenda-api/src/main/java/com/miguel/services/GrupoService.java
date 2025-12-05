package com.miguel.services;

import com.miguel.persistence.entities.Grupo;
import com.miguel.persistence.repositories.GrupoRepository;
import com.miguel.services.dtos.GrupoRequest;
import com.miguel.services.dtos.GrupoResponse;
import com.miguel.services.exceptions.GrupoException;
import com.miguel.services.exceptions.GrupoNotFoundException;
import com.miguel.services.mappers.GrupoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    public List<GrupoResponse> getGrupos(int idUsuario){
        return grupoRepository.findByIdUsuario(idUsuario).stream()
                .map(GrupoMapper::toDto)
                .toList();
    }

    public GrupoResponse getGrupo(int idGrupo, int idUsuario){
        Optional<Grupo> grupo = grupoRepository.findByIdAndIdUsuario(idGrupo, idUsuario);
        if(grupo.isEmpty()){
            throw new GrupoNotFoundException("Grupo no encontrado");
        }
        return GrupoMapper.toDto(grupo.get());
    }

    public List<GrupoResponse> getGruposByNombre(String nombre, int idUsuario){
        return grupoRepository.findByIdUsuarioAndNombreContainingIgnoreCase(idUsuario, nombre).stream()
                .map(GrupoMapper::toDto)
                .toList();
    }

    public GrupoResponse createGrupo(GrupoRequest grupoRequest, int idUsuario){
        if(grupoRepository.existsByIdUsuarioAndNombreIgnoreCase(idUsuario, grupoRequest.getNombre())){
            throw new GrupoException("Grupo ya existente");
        }

        grupoRequest.setId(0);
        Grupo g = GrupoMapper.toEntity(grupoRequest);
        g.setIdUsuario(idUsuario);

        return GrupoMapper.toDto(grupoRepository.save(g));
    }

    public GrupoResponse updateGrupo(int id, GrupoRequest grupoRequest, int idUsuario){
        if(id != grupoRequest.getId()){
            throw new GrupoException("El id del path y el body no coinciden");
        }
        if(grupoRepository.findByIdAndIdUsuario(id, idUsuario).isEmpty()){
            throw new GrupoNotFoundException("Grupo no encontrado");
        }
        if(grupoRepository.existsByIdUsuarioAndNombreIgnoreCase(idUsuario, grupoRequest.getNombre())){
            throw new GrupoException("Grupo ya existente");
        }

        Grupo g = GrupoMapper.toEntity(grupoRequest);
        g.setIdUsuario(idUsuario);

        return GrupoMapper.toDto(grupoRepository.save(g));
    }

    public String deleteGrupo(int id, int idUsuario){
        Optional<Grupo> grupo = grupoRepository.findByIdAndIdUsuario(id, idUsuario);
        if(grupo.isEmpty()){
            throw new GrupoNotFoundException("Grupo no encontrado");
        }

        String nombre = grupo.get().getNombre();
        grupoRepository.deleteById(id);

        return nombre;
    }

    // OTROS
    public GrupoResponse updateNombre(int id, String nombre, int idUsuario){
        Optional<Grupo> grupo = grupoRepository.findByIdAndIdUsuario(id, idUsuario);
        if(grupo.isEmpty()){
            throw new GrupoNotFoundException("Grupo no encontrado");
        }
        if(grupoRepository.existsByIdUsuarioAndNombreIgnoreCase(idUsuario, nombre)){
            throw new GrupoException("Grupo con nombre ya existente");
        }
        if(nombre == null || nombre.isBlank()){
            throw new GrupoException("El nombre no puede estar vacio");
        }

        grupo.get().setNombre(nombre);
        grupoRepository.save(grupo.get());

        return GrupoMapper.toDto(grupo.get());
    }

    // Para Anotacion y Clase
    public boolean perteneceAUsuario(int idGrupo, int idUsuario) {
        return grupoRepository.existsByIdAndIdUsuario(idGrupo, idUsuario);
    }

}
