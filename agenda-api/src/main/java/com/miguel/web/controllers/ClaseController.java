package com.miguel.web.controllers;

import com.miguel.security.user.User;
import com.miguel.services.ClaseService;
import com.miguel.services.dtos.ClaseRequest;
import com.miguel.services.exceptions.ClaseException;
import com.miguel.services.exceptions.ClaseNotFoundException;
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
    private ClaseService claseService;

    // CRUDs
    // ADMIN
    @GetMapping
    public ResponseEntity<?> getClases(@AuthenticationPrincipal User user) {
        try{
            return ResponseEntity.ok(claseService.findAll(user));
        }catch (WrongUserException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/grupo/{idGrupo}")
    public ResponseEntity<?> getClasesByIdGrupo(@PathVariable int idGrupo, @AuthenticationPrincipal User user){
        try {
            return ResponseEntity.ok(claseService.findByGrupo(idGrupo, user.getId()));
        }catch (WrongUserException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    // ADMIN
    @GetMapping("/{id}")
    public ResponseEntity<?> getClase(@PathVariable int id, @AuthenticationPrincipal User user) {
        try{
            return ResponseEntity.ok(claseService.findById(id, user));
        }catch(ClaseNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (WrongUserException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/grupo/{idGrupo}")
    public ResponseEntity<?> createClase(@PathVariable int idGrupo, @RequestBody ClaseRequest claseRequest, @AuthenticationPrincipal User user) {
        try{
            return ResponseEntity.ok(claseService.create(idGrupo, claseRequest, user.getId()));
        }catch (WrongUserException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }catch (ClaseException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/grupo/{idGrupo}/{id}")
    public ResponseEntity<?> updateClase(@PathVariable int idGrupo, @PathVariable int id, @RequestBody ClaseRequest claseRequest, @AuthenticationPrincipal User user) {
        try{
            return ResponseEntity.ok(claseService.update(idGrupo, id, claseRequest, user.getId()));
        }catch (WrongUserException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }catch (ClaseException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/grupo/{idGrupo}/{id}")
    public ResponseEntity<?> deleteClase(@PathVariable int idGrupo, @PathVariable int id, @AuthenticationPrincipal User user) {
        try{
            claseService.delete(idGrupo, id, user.getId());
            return ResponseEntity.ok("Clase " + id + " eliminada con éxito.");
        }catch (ClaseException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (WrongUserException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    // USABLES
    @PutMapping("/grupo/{idGrupo}/{id}/aula")
    public ResponseEntity<?> cambiarAula(@PathVariable int idGrupo, @PathVariable int id, @RequestParam String aula, @AuthenticationPrincipal User user) {
        try{
            return ResponseEntity.ok(claseService.cambiarAula(idGrupo, id, aula, user.getId()));
        }catch (ClaseException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (WrongUserException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
