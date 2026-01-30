package com.miguel.web.controllers;

import com.miguel.persistence.entities.user.User;
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
    public ResponseEntity<?> getClasesByIdGrupo(@PathVariable int idGrupo, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(grupoService.findClasesByGrupo(idGrupo, user.getId()));
    }

    //findById
    @GetMapping("/grupo/{idGrupo}/{idClase}")
    public ResponseEntity<?> getClaseById(@PathVariable int idGrupo, @PathVariable int idClase,
                                          @AuthenticationPrincipal User user){
        return ResponseEntity.ok(grupoService.findClase(idGrupo, idClase, user.getId()));
    }

    //create
    @PostMapping("/grupo/{idGrupo}")
    public ResponseEntity<?> createClase(@PathVariable int idGrupo, @RequestBody ClaseRequest claseRequest,
                                         @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(grupoService.createClase(idGrupo, claseRequest, user.getId()));
    }

    //update
    @PutMapping("/grupo/{idGrupo}/{idClase}")
    public ResponseEntity<?> updateClase(@PathVariable int idGrupo, @PathVariable int idClase,
                                         @RequestBody ClaseRequest claseRequest, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(grupoService.updateClase(idGrupo, idClase, claseRequest, user.getId()));
    }

    //delete
    @DeleteMapping("/grupo/{idGrupo}/{idClase}")
    public ResponseEntity<?> deleteClase(@PathVariable int idGrupo, @PathVariable int idClase,
                                         @AuthenticationPrincipal User user) {
        this.grupoService.deleteClase(idGrupo, idClase, user.getId());
        return ResponseEntity.ok("Clase " + idClase + " eliminada con éxito.");
    }

}
