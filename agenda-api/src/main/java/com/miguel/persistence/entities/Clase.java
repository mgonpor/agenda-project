package com.miguel.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;

@Entity
@Table(name = "clase")
@Setter
@Getter
@NoArgsConstructor
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_grupo")
    private int idGrupo;

    @ManyToOne
    @JoinColumn(name = "id_grupo", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Grupo grupo;

    @Column(name = "dia_semana", nullable = false)
    @Enumerated(EnumType.STRING)
    private DayOfWeek diaSemana;

    @Column(nullable = false)
    private int tramo;

    private String aula;

}
