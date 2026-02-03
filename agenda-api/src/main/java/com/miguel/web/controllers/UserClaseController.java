package com.miguel.web.controllers;

import com.miguel.persistence.entities.Usuario;
import com.miguel.services.GrupoService;
import com.miguel.services.dtos.ClaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/clase")
public class UserClaseController {

    @Autowired
    private GrupoService grupoService;

    //CRUDs
    // findAll
    @GetMapping("/grupo/{idGrupo}")
    public ResponseEntity<?> getClasesByIdGrupo(@PathVariable int idGrupo, @AuthenticationPrincipal Usuario usuario){
        return ResponseEntity.ok(grupoService.findClasesByGrupo(idGrupo, usuario.getId()));
    }

    //findById
    @GetMapping("/grupo/{idGrupo}/{idClase}")
    public ResponseEntity<?> getClaseById(@PathVariable int idGrupo, @PathVariable int idClase,
                                          @AuthenticationPrincipal Usuario usuario){
        return ResponseEntity.ok(grupoService.findClase(idGrupo, idClase, usuario.getId()));
    }

    //create
    @PostMapping("/grupo/{idGrupo}")
    public ResponseEntity<?> createClase(@PathVariable int idGrupo, @RequestBody ClaseRequest claseRequest,
                                         @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(grupoService.createClase(idGrupo, claseRequest, usuario.getId()));
    }

    //update
    @PutMapping("/grupo/{idGrupo}/{idClase}")
    public ResponseEntity<?> updateClase(@PathVariable int idGrupo, @PathVariable int idClase,
                                         @RequestBody ClaseRequest claseRequest, @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(grupoService.updateClase(idGrupo, idClase, claseRequest, usuario.getId()));
    }

    //delete
    @DeleteMapping("/grupo/{idGrupo}/{idClase}")
    public ResponseEntity<?> deleteClase(@PathVariable int idGrupo, @PathVariable int idClase,
                                         @AuthenticationPrincipal Usuario usuario) {
        this.grupoService.deleteClase(idGrupo, idClase, usuario.getId());
        return ResponseEntity.ok("Clase " + idClase + " eliminada con éxito.");
    }

}
