package com.miguel.web.controllers;

import com.miguel.security.user.User;
import com.miguel.services.GrupoService;
import com.miguel.services.dtos.AnotacionDto;
import com.miguel.services.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/anotaciones")
public class AnotacionController {

    @Autowired
    private GrupoService grupoService;

    // CRUDs
    @GetMapping("/grupo/{idGrupo}")
    public ResponseEntity<?> getAnotaciones(@PathVariable int idGrupo, @AuthenticationPrincipal User user){
        try {
            return ResponseEntity.ok(grupoService.findAnotacionesByGrupo(idGrupo, user.getId()));
        } catch (GrupoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/grupo/{idGrupo}/{idAnotacion}")
    public ResponseEntity<?> getAnotacion(@PathVariable int idGrupo, @PathVariable int idAnotacion, @AuthenticationPrincipal User user){
        try{
            return ResponseEntity.ok(grupoService.findAnotacion(idGrupo, idAnotacion, user.getId()));
        }catch (GrupoNotFoundException | AnotacionNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/grupo/{idGrupo}")
    public ResponseEntity<?> createAnotacion(@PathVariable int idGrupo, @RequestBody AnotacionDto anotacionRequest, @AuthenticationPrincipal User user){
        try{
            return ResponseEntity.ok(grupoService.createAnotacion(idGrupo, anotacionRequest, user.getId()));
        }catch (GrupoNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (AnotacionException | EmptyTextException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/grupo/{idGrupo}/{idAnotacion}")
    public ResponseEntity<?> updateAnotacion(@PathVariable int idGrupo, @PathVariable int idAnotacion,
                                             @RequestBody AnotacionDto anotacionRequest, @AuthenticationPrincipal User user){
        try{
            return ResponseEntity.ok(grupoService.updateAnotacion(idGrupo, idAnotacion, anotacionRequest, user.getId()));
        }catch (GrupoNotFoundException | AnotacionNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (AnotacionException | EmptyTextException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{idGrupo}/{idAnotacion}")
    public ResponseEntity<?> deleteAnotacion(@PathVariable int idGrupo, @PathVariable int idAnotacion,
                                             @AuthenticationPrincipal User user){
        try{
            grupoService.deleteAnotacion(idGrupo, idAnotacion, user.getId());
            return ResponseEntity.ok("Anotación eliminada con éxito.");
        }catch (GrupoNotFoundException | AnotacionNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
