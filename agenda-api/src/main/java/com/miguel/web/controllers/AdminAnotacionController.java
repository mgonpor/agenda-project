package com.miguel.web.controllers;

import com.miguel.persistence.entities.user.User;
import com.miguel.services.AnotacionService;
import com.miguel.services.GrupoService;
import com.miguel.services.dtos.AnotacionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/anotacion")
public class AdminAnotacionController {

    @Autowired
    private GrupoService grupoService;

    //Para admin
    @Autowired
    private AnotacionService anotacionService;

    // CRUDs ADMIN
    @GetMapping
    public ResponseEntity<?> getAllAnotaciones(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(this.anotacionService.findAll(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnotacion(@PathVariable int id, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(anotacionService.findByIdAdmin(id, user));
    }

    // Aux por grupoService
    @PostMapping
    public ResponseEntity<?> createAnotacionAdmin(@RequestParam int idGrupo, @RequestParam int idUsuario,
                                                  @RequestBody AnotacionDto anotacionRequest, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(this.grupoService.createAnotacionAdmin(idGrupo, idUsuario, anotacionRequest, user));
    }

    @PutMapping("/{idAnotacion}")
    public ResponseEntity<?> updateAnotacionAdmin(@PathVariable int idAnotacion,@RequestParam int idGrupo, @RequestParam int idUsuario,
                                                  @RequestBody AnotacionDto anotacionRequest, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(this.grupoService.updateAnotacionAdmin(idAnotacion, idGrupo, idUsuario, anotacionRequest, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnotacion(@PathVariable int id, @AuthenticationPrincipal User user){
        this.anotacionService.deleteAdmin(id, user);
        return ResponseEntity.ok("Anotación " + id + " eliminada con éxito.");
    }

}
