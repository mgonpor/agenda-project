package com.miguel.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "anotacion")
@Setter
@Getter
@NoArgsConstructor
public class Anotacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_grupo")
    private int idGrupo;

    @ManyToOne
    @JoinColumn(name = "id_grupo", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Grupo grupo;

    @Column(unique = true)
    private LocalDate fecha;

    @Column(nullable = false)
    private String texto;

}
