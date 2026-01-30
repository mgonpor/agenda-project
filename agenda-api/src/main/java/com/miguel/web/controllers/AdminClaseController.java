package com.miguel.web.controllers;

import com.miguel.persistence.entities.user.User;
import com.miguel.services.ClaseService;
import com.miguel.services.GrupoService;
import com.miguel.services.dtos.ClaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/clase")
public class AdminClaseController {

    @Autowired
    private GrupoService grupoService;

    // Para admin
    @Autowired
    private ClaseService claseService;

    //CRUDs ADMIN
    @GetMapping
    public ResponseEntity<?> getClases(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(this.claseService.findAll(user));
    }

    @GetMapping("/{idClase}")
    public ResponseEntity<?> getClase(@PathVariable int idClase, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(this.claseService.findById(idClase, user));
    }

    @PostMapping
    public ResponseEntity<?> createClaseAdmin(@RequestParam int idGrupo, @RequestParam int idUsuario,
                                              @RequestBody ClaseRequest claseRequest, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(this.grupoService.createClaseAdmin(idGrupo, idUsuario, claseRequest, user));
    }

    @PutMapping("/{idClase}")
    public ResponseEntity<?> updateClaseAdmin(@PathVariable int idClase, @RequestParam int idGrupo, @RequestParam int idUsuario,
                                              @RequestBody ClaseRequest claseRequest, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(this.grupoService.updateClaseAdmin(idClase, idGrupo, idUsuario, claseRequest, user));
    }

    @DeleteMapping("/{idClase}")
    public ResponseEntity<?> deleteClase(@PathVariable int idClase, @AuthenticationPrincipal User user) {
        this.claseService.deleteAdmin(idClase, user);
        return ResponseEntity.ok("Clase " + idClase + " eliminada con éxito.");
    }

}
