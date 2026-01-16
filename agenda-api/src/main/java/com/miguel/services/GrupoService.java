package com.miguel.services;

import com.miguel.persistence.entities.Grupo;
import com.miguel.persistence.repositories.GrupoRepository;
import com.miguel.services.dtos.*;
import com.miguel.services.exceptions.ClaseException;
import com.miguel.services.exceptions.GrupoException;
import com.miguel.services.exceptions.GrupoNotFoundException;
import com.miguel.services.mappers.GrupoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private ClaseService claseService;

    @Autowired
    private AnotacionService anotacionService;

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
    public List<GrupoResponse> getGruposByNombre(String nombre, int idUsuario){
        return grupoRepository.findByIdUsuarioAndNombreContainingIgnoreCase(idUsuario, nombre).stream()
                .map(GrupoMapper::toDto)
                .toList();
    }

    // CRUDS Clase
    public List<ClaseResponse> findClasesByGrupo(int idGrupo, int idUsuario){
        if (!grupoRepository.existsByIdAndIdUsuario(idGrupo, idUsuario)) {
            throw new GrupoNotFoundException("Grupo no encontrado");
        }

        return this.claseService.findByGrupo(idGrupo);
    }

    public ClaseResponse findClase(int idGrupo, int idClase, int idUsuario){
        if (!grupoRepository.existsByIdAndIdUsuario(idGrupo, idUsuario)) {
            throw new GrupoNotFoundException("Grupo no encontrado");
        }

        return this.claseService.findById(idGrupo, idClase);
    }

    public ClaseResponse createClase(int idGrupo, ClaseRequest claseRequest, int idUsuario){
        if (!grupoRepository.existsByIdAndIdUsuario(idGrupo, idUsuario)) {
            throw new GrupoNotFoundException("Grupo no encontrado");
        }
        if(idGrupo != claseRequest.getIdGrupo()) {
            throw new ClaseException("El id del grupo del path y el body no coinciden");
        }

        return this.claseService.create(claseRequest);
    }

    public ClaseResponse updateClase(int idGrupo, int idClase, ClaseRequest claseRequest, int idUsuario){
        if (!grupoRepository.existsByIdAndIdUsuario(idGrupo, idUsuario)) {
            throw new GrupoNotFoundException("Grupo no encontrado");
        }
        if(idGrupo != claseRequest.getIdGrupo()) {
            throw new ClaseException("El id del grupo del path y el body no coinciden");
        }
        return this.claseService.update(idClase, claseRequest, idUsuario);
    }

    public void deleteClase(int idGrupo, int idClase, int idUsuario){
        if (!grupoRepository.existsByIdAndIdUsuario(idGrupo, idUsuario)) {
            throw new GrupoNotFoundException("Grupo no encontrado");
        }
        this.claseService.delete(idGrupo, idClase);
    }

    //CRUDs Anotacion
    public List<AnotacionDto> findAnotacionesByGrupo(int idGrupo, int idUsuario){
        if (!grupoRepository.existsByIdAndIdUsuario(idGrupo, idUsuario)) {
            throw new GrupoNotFoundException("Grupo no encontrado");
        }
        return this.anotacionService.findByGrupo(idGrupo);
    }

    public AnotacionDto findAnotacion(int idGrupo, int idAnotacion, int idUsuario){
        if (!grupoRepository.existsByIdAndIdUsuario(idGrupo, idUsuario)) {
            throw new GrupoNotFoundException("Grupo no encontrado");
        }
        return this.anotacionService.findById(idGrupo, idAnotacion);
    }

    // todo
    public AnotacionDto createAnotacion(int idGrupo, AnotacionDto anotacionDto, int idUsuario){}

    public AnotacionDto updateAnotacion(int idGrupo, int idAnotacion, AnotacionDto anotacionDto, int idUsuario){}

    public void deleteAnotacion(int idGrupo, int idAnotacion, int idUsuario){}

}
