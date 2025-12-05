package com.miguel.persistence.repositories;

import com.miguel.persistence.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GrupoRepository extends JpaRepository<Grupo, Integer> {

    List<Grupo> findByIdUsuario(int idUsuario);

    Optional<Grupo> findByIdAndIdUsuario(int id, int idUsuario);

    List<Grupo> findByIdUsuarioAndNombreContainingIgnoreCase(int idUsuario, String nombre);

    boolean existsByIdUsuarioAndNombreIgnoreCase(int idUsuario, String nombre);

// Para Anotacion y Clase
    boolean existsByIdAndIdUsuario(int id, int idUsuario);

}
