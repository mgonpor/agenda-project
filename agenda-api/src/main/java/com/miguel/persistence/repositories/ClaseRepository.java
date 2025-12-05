package com.miguel.persistence.repositories;

import com.miguel.persistence.entities.Clase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface ClaseRepository extends JpaRepository<Clase, Integer> {

    List<Clase> findByIdGrupo(int idGrupo);

    Optional<Clase> findByDiaSemanaAndTramo(DayOfWeek diaSemana, int tramo);

    boolean existsByIdAndIdGrupo(int id, int idGrupo);
}
