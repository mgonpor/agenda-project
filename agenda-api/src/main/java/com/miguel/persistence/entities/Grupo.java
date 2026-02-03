package com.miguel.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "grupo")
@Setter
@Getter
@NoArgsConstructor
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_usuario")
    private int idUsuario;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Usuario usuario;

    @Column(columnDefinition = "VARCHAR(20)",  nullable = false, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "grupo")
    @JsonIgnore
    private List<Clase> clases;

    @OneToMany(mappedBy = "grupo")
    private List<Anotacion> anotaciones;

}
