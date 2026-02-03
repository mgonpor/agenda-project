package com.miguel.services;

import com.miguel.persistence.entities.Grupo;
import com.miguel.persistence.repositories.GrupoRepository;
import com.miguel.persistence.entities.Usuario;
import com.miguel.persistence.repositories.UsuarioRepository;
import com.miguel.services.dtos.*;
import com.miguel.services.exceptions.GrupoException;
import com.miguel.services.exceptions.GrupoNotFoundException;
import com.miguel.services.exceptions.UserNotFoundException;
import com.miguel.services.exceptions.WrongUserException;
import com.miguel.services.mappers.GrupoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ClaseService claseService;
    @Autowired
    private AnotacionService anotacionService;

    // --- MÉTODOS AUXILIARES DE CONTEXTO ---

    private Usuario getUsuarioAutenticado() {
        return usuarioRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UserNotFoundException("Usuario en sesión no encontrado"));
    }

    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
    /**
     * Verifica si el grupo pertenece al usuario o si es ADMIN.
     */
    private void validarAccesoGrupo(int idGrupo) {
        if (isAdmin()) return; // Si es admin, tiene acceso total

        Usuario usuario = getUsuarioAutenticado();
        if (!grupoRepository.existsByIdAndIdUsuario(idGrupo, usuario.getId())) {
            throw new GrupoNotFoundException("El grupo no existe o no tienes permiso");
        }
    }

    // ==========================================
    // SECCIÓN USER (Solo recursos propios)
    // ==========================================

    public List<GrupoResponse> getGruposUser() {
        return grupoRepository.findByIdUsuario(getUsuarioAutenticado().getId()).stream()
                .map(GrupoMapper::toDto).toList();
    }

    public GrupoResponse getGrupoUser(int idGrupo) {
        validarAccesoGrupo(idGrupo);
        return GrupoMapper.toDto(grupoRepository.findById(idGrupo).get());
    }

    public GrupoResponse createGrupoUser(GrupoRequest grupoRequest) {
        Usuario usuario = getUsuarioAutenticado();
        if (grupoRepository.existsByIdUsuarioAndNombreIgnoreCase(usuario.getId(), grupoRequest.getNombre())) {
            throw new GrupoException("Ya tienes un grupo con ese nombre");
        }
        Grupo g = GrupoMapper.toEntity(grupoRequest);
        g.setId(0);
        g.setIdUsuario(usuario.getId());
        return GrupoMapper.toDto(grupoRepository.save(g));
    }

    public GrupoResponse updateGrupoUser(int id, GrupoRequest grupoRequest) {
        validarAccesoGrupo(id);
        // Lógica de actualización igual a la anterior pero garantizando que es del usuario
        Grupo grupoBD = grupoRepository.findById(id).get();
        grupoBD.setNombre(grupoRequest.getNombre());
        return GrupoMapper.toDto(grupoRepository.save(grupoBD));
    }

    public String deleteGrupoUser(int id) {
        validarAccesoGrupo(id);
        String nombre = grupoRepository.findById(id).get().getNombre();
        grupoRepository.deleteById(id);
        return nombre;
    }

    // --- CLASES ---
    public List<ClaseResponse> findClasesByGrupoUser(int idGrupo) {
        validarAccesoGrupo(idGrupo); // Seguridad primero
        return this.claseService.findByGrupo(idGrupo);
    }

    public ClaseResponse findClaseByIdUser(int idGrupo, int idClase) {
        validarAccesoGrupo(idGrupo);
        return this.claseService.findById(idGrupo, idClase);
    }

    public ClaseResponse createClaseUser(int idGrupo, ClaseRequest request) {
        validarAccesoGrupo(idGrupo);
        return this.claseService.create(idGrupo, request);
    }

    public ClaseResponse updateClaseUser(int idGrupo, int idClase, ClaseRequest request) {
        validarAccesoGrupo(idGrupo);
        return this.claseService.update(idGrupo, idClase, request);
    }

    public void deleteClaseUser(int idGrupo, int idClase) {
        validarAccesoGrupo(idGrupo);
        this.claseService.delete(idGrupo, idClase);
    }

    // --- ANOTACIONES ---
    public List<AnotacionDto> findAnotacionesByGrupoUser(int idGrupo) {
        validarAccesoGrupo(idGrupo);
        return this.anotacionService.findByGrupo(idGrupo);
    }

    public AnotacionDto findAnotacionByIdUser(int idGrupo, int idAnotacion) {
        validarAccesoGrupo(idGrupo);
        return this.anotacionService.findById(idGrupo, idAnotacion);
    }

    public AnotacionDto createAnotacionUser(int idGrupo, AnotacionDto request) {
        validarAccesoGrupo(idGrupo);
        return this.anotacionService.createAnotacion(idGrupo, request);
    }

    public AnotacionDto updateAnotacionUser(int idGrupo, int idAnotacion, AnotacionDto request) {
        validarAccesoGrupo(idGrupo);
        return this.anotacionService.updateAnotacion(idGrupo, idAnotacion, request);
    }

    public void deleteAnotacionUser(int idGrupo, int idAnotacion) {
        validarAccesoGrupo(idGrupo);
        this.anotacionService.delete(idGrupo, idAnotacion);
    }

    // ==========================================
    // SECCIÓN ADMIN (Acceso Total)
    // ==========================================

    public List<GrupoResponse> getAllGruposAdmin() {
        return grupoRepository.findAll().stream().map(GrupoMapper::toDto).toList();
    }

    public GrupoResponse getGrupoAdmin(int idGrupo) {
        return grupoRepository.findById(idGrupo)
                .map(GrupoMapper::toDto)
                .orElseThrow(() -> new GrupoNotFoundException("Grupo no existe"));
    }

    public GrupoResponse createGrupoAdmin(GrupoRequest grupoRequest, int idUsuarioDestino) {
        if (!usuarioRepository.existsById(idUsuarioDestino)) {
            throw new UserNotFoundException("Usuario destino no encontrado");
        }
        // Lógica idéntica: Misma validación de nombre duplicado
        if (grupoRepository.existsByIdUsuarioAndNombreIgnoreCase(idUsuarioDestino, grupoRequest.getNombre())) {
            throw new GrupoException("El usuario ya tiene un grupo con ese nombre");
        }

        Grupo g = GrupoMapper.toEntity(grupoRequest);
        g.setId(0);
        g.setIdUsuario(idUsuarioDestino);
        return GrupoMapper.toDto(grupoRepository.save(g));
    }

    public GrupoResponse updateGrupoAdmin(int idGrupo, GrupoRequest grupoRequest, int idUsuarioDestino) {
        // Paridad: Validación de coherencia de IDs como en el métod original
        if (idGrupo != grupoRequest.getId()) {
            throw new GrupoException("ID de ruta y body no coinciden");
        }

        Grupo grupoBD = grupoRepository.findById(idGrupo)
                .orElseThrow(() -> new GrupoNotFoundException("Grupo no encontrado"));

        // Paridad: Validación de nombre duplicado para ese usuario específico
        if (!grupoBD.getNombre().equalsIgnoreCase(grupoRequest.getNombre()) &&
                grupoRepository.existsByIdUsuarioAndNombreIgnoreCase(idUsuarioDestino, grupoRequest.getNombre())) {
            throw new GrupoException("Ya existe otro grupo con ese nombre para este usuario");
        }

        grupoBD.setNombre(grupoRequest.getNombre());
        grupoBD.setIdUsuario(idUsuarioDestino); // El admin puede reasignar el grupo

        return GrupoMapper.toDto(grupoRepository.save(grupoBD));
    }

    public String deleteGrupoAdmin(int idGrupo) {
        if (!grupoRepository.existsById(idGrupo)) throw new GrupoNotFoundException("No existe");
        String nombre = grupoRepository.findById(idGrupo).get().getNombre();
        grupoRepository.deleteById(idGrupo);
        return nombre;
    }

    public List<GrupoResponse> findGruposByNombreAdmin(String nombre) {
        // Búsqueda global para el admin
        return grupoRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(GrupoMapper::toDto).toList();
    }

    // --- Sub-recursos para ADMIN (Acceso a cualquier grupo) ---

    public List<ClaseResponse> findClasesByGrupoAdmin(int idGrupo) {
        return this.claseService.findByGrupo(idGrupo); // Sin validar propiedad
    }

    public List<AnotacionDto> findAnotacionesByGrupoAdmin(int idGrupo) {
        return this.anotacionService.findByGrupo(idGrupo); // Sin validar propiedad
    }

}
