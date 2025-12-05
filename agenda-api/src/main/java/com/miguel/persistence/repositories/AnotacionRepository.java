package com.miguel.persistence.repositories;

import com.miguel.persistence.entities.Anotacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AnotacionRepository extends JpaRepository<Anotacion, Integer> {

    List<Anotacion> findByIdGrupo(int idGrupo);

    Optional<Anotacion> findByIdGrupoAndFecha(int idGrupo, LocalDate fecha);

    boolean existsByIdAndIdGrupo(int id, int idGrupo);
}
