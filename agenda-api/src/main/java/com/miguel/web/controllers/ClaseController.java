package com.miguel.web.controllers;

import com.miguel.security.user.User;
import com.miguel.services.ClaseService;
import com.miguel.services.GrupoService;
import com.miguel.services.dtos.ClaseRequest;
import com.miguel.services.exceptions.ClaseException;
import com.miguel.services.exceptions.ClaseNotFoundException;
import com.miguel.services.exceptions.GrupoNotFoundException;
import com.miguel.services.exceptions.WrongUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clases")
public class ClaseController {

    @Autowired
    private GrupoService grupoService;

    //CRUDs

    // findAll
    @GetMapping("/grupo/{idGrupo}")
    public ResponseEntity<?> getClasesByIdGrupo(@PathVariable int idGrupo, @AuthenticationPrincipal User user){
        try {
            return ResponseEntity.ok(grupoService.findClasesByGrupo(idGrupo, user.getId()));
        }catch (GrupoNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //findById
    @GetMapping("/grupo/{idGrupo}/{idClase}")
    public ResponseEntity<?> getClaseById(@PathVariable int idGrupo, @PathVariable int idClase,
                                          @AuthenticationPrincipal User user){
        try{
            return ResponseEntity.ok(grupoService.findClase(idGrupo, idClase, user.getId()));
        }catch (GrupoNotFoundException | ClaseNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //create
    @PostMapping("/grupo/{idGrupo}")
    public ResponseEntity<?> createClase(@PathVariable int idGrupo, @RequestBody ClaseRequest claseRequest,
                                         @AuthenticationPrincipal User user) {
        try{
            return ResponseEntity.ok(grupoService.createClase(idGrupo, claseRequest, user.getId()));
        }catch (GrupoNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (ClaseException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //update
    @PutMapping("/grupo/{idGrupo}/{idClase}")
    public ResponseEntity<?> updateClase(@PathVariable int idGrupo, @PathVariable int idClase,
                                         @RequestBody ClaseRequest claseRequest, @AuthenticationPrincipal User user) {
        try{
            return ResponseEntity.ok(grupoService.updateClase(idGrupo, idClase, claseRequest, user.getId()));
        }catch (GrupoNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (ClaseException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //delete
    @DeleteMapping("/grupo/{idGrupo}/{idClase}")
    public ResponseEntity<?> deleteClase(@PathVariable int idGrupo, @PathVariable int idClase,
                                         @AuthenticationPrincipal User user) {
        try{
            this.grupoService.deleteClase(idGrupo, idClase, user.getId());
            return ResponseEntity.ok("Clase " + idClase + " eliminada con éxito.");
        }catch (ClaseException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (GrupoNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ADMIN
    @GetMapping("/{id}")
    public ResponseEntity<?> getClase(@PathVariable int id, @AuthenticationPrincipal User user) {
        try{
            return ResponseEntity.ok();
        }catch(ClaseNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (WrongUserException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
