package com.miguel.web.controllers;

import com.miguel.persistence.entities.user.User;
import com.miguel.services.GrupoService;
import com.miguel.services.dtos.AnotacionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/anotacion")
public class UserAnotacionController {

    @Autowired
    private GrupoService grupoService;

    // CRUDs
    @GetMapping("/grupo/{idGrupo}")
    public ResponseEntity<?> getAnotaciones(@PathVariable int idGrupo, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(grupoService.findAnotacionesByGrupo(idGrupo, user.getId()));
    }

    @GetMapping("/grupo/{idGrupo}/{idAnotacion}")
    public ResponseEntity<?> getAnotacion(@PathVariable int idGrupo, @PathVariable int idAnotacion, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(grupoService.findAnotacion(idGrupo, idAnotacion, user.getId()));
    }

    @PostMapping("/grupo/{idGrupo}")
    public ResponseEntity<?> createAnotacion(@PathVariable int idGrupo, @RequestBody AnotacionDto anotacionRequest, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(grupoService.createAnotacion(idGrupo, anotacionRequest, user.getId()));
    }

    @PutMapping("/grupo/{idGrupo}/{idAnotacion}")
    public ResponseEntity<?> updateAnotacion(@PathVariable int idGrupo, @PathVariable int idAnotacion,
                                             @RequestBody AnotacionDto anotacionRequest, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(grupoService.updateAnotacion(idGrupo, idAnotacion, anotacionRequest, user.getId()));
    }

    @DeleteMapping("/{idGrupo}/{idAnotacion}")
    public ResponseEntity<?> deleteAnotacion(@PathVariable int idGrupo, @PathVariable int idAnotacion,
                                             @AuthenticationPrincipal User user){
        grupoService.deleteAnotacion(idGrupo, idAnotacion, user.getId());
        return ResponseEntity.ok("Anotación eliminada con éxito.");
    }

}
