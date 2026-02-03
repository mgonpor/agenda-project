package com.miguel.web.controllers;

import com.miguel.services.GrupoService;
import com.miguel.services.dtos.AnotacionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/anotacion")
public class UserAnotacionController {

    @Autowired
    private GrupoService grupoService;

    // CRUDs
    @GetMapping("/grupo/{idGrupo}")
    public ResponseEntity<?> getAnotaciones(@PathVariable int idGrupo){
        return ResponseEntity.ok(grupoService.findAnotacionesByGrupoUser(idGrupo));
    }

    @GetMapping("/grupo/{idGrupo}/{idAnotacion}")
    public ResponseEntity<?> getAnotacion(@PathVariable int idGrupo, @PathVariable int idAnotacion){
        return ResponseEntity.ok(grupoService.findAnotacionByIdUser(idGrupo, idAnotacion));
    }

    @PostMapping("/grupo/{idGrupo}")
    public ResponseEntity<?> createAnotacion(@PathVariable int idGrupo, @RequestBody AnotacionDto anotacionRequest){
        return ResponseEntity.ok(grupoService.createAnotacionUser(idGrupo, anotacionRequest));
    }

    @PutMapping("/grupo/{idGrupo}/{idAnotacion}")
    public ResponseEntity<?> updateAnotacion(@PathVariable int idGrupo, @PathVariable int idAnotacion,
                                             @RequestBody AnotacionDto anotacionRequest){
        return ResponseEntity.ok(grupoService.updateAnotacionUser(idGrupo, idAnotacion, anotacionRequest));
    }

    @DeleteMapping("/{idGrupo}/{idAnotacion}")
    public ResponseEntity<?> deleteAnotacion(@PathVariable int idGrupo, @PathVariable int idAnotacion){
        grupoService.deleteAnotacionUser(idGrupo, idAnotacion);
        return ResponseEntity.ok("Anotación eliminada con éxito.");
    }

}
