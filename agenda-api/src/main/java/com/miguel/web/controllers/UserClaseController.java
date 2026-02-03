package com.miguel.web.controllers;

import com.miguel.services.GrupoService;
import com.miguel.services.dtos.ClaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/clase")
public class UserClaseController {

    @Autowired
    private GrupoService grupoService;

    //CRUDs
    // findAll
    @GetMapping("/grupo/{idGrupo}")
    public ResponseEntity<?> getClasesByIdGrupo(@PathVariable int idGrupo){
        return ResponseEntity.ok(grupoService.findClasesByGrupoUser(idGrupo));
    }

    //findById
    @GetMapping("/grupo/{idGrupo}/{idClase}")
    public ResponseEntity<?> getClaseById(@PathVariable int idGrupo, @PathVariable int idClase){
        return ResponseEntity.ok(grupoService.findClaseByIdUser(idGrupo, idClase));
    }

    //create
    @PostMapping("/grupo/{idGrupo}")
    public ResponseEntity<?> createClase(@PathVariable int idGrupo, @RequestBody ClaseRequest claseRequest) {
        return ResponseEntity.ok(grupoService.createClaseUser(idGrupo, claseRequest));
    }

    //update
    @PutMapping("/grupo/{idGrupo}/{idClase}")
    public ResponseEntity<?> updateClase(@PathVariable int idGrupo, @PathVariable int idClase,
                                         @RequestBody ClaseRequest claseRequest) {
        return ResponseEntity.ok(grupoService.updateClaseUser(idGrupo, idClase, claseRequest));
    }

    //delete
    @DeleteMapping("/grupo/{idGrupo}/{idClase}")
    public ResponseEntity<?> deleteClase(@PathVariable int idGrupo, @PathVariable int idClase) {
        this.grupoService.deleteClaseUser(idGrupo, idClase);
        return ResponseEntity.ok("Clase " + idClase + " eliminada con éxito.");
    }

}
