package com.miguel.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String username;
    private String password;

    @Column(unique = true)
    private String email;
    private String rol;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY) //Con LAZY solo cargamos las tareas cuando las utilicemos (más eficiente)
    @JsonIgnore
    private List<Grupo> grupos;

}
