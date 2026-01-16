package com.miguel.persistence.repositories;

import com.miguel.persistence.entities.Clase;
import com.miguel.services.dtos.ClaseResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface ClaseRepository extends JpaRepository<Clase, Integer> {

    List<Clase> findByIdGrupo(int idGrupo);

    Optional<Clase> findByDiaSemanaAndTramoAndGrupoUsuarioId(DayOfWeek diaSemana, int tramo, int idUsuario);

    boolean existsByIdAndIdGrupo(int id, int idGrupo);

    Optional<Clase> findByIdAndIdGrupo(int id, int idGrupo);
}
