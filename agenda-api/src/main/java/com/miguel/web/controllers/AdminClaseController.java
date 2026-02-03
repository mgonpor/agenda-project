package com.miguel.web.controllers;

import com.miguel.persistence.entities.Usuario;
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
    public ResponseEntity<?> getClases(@AuthenticationPrincipal Usuario usuario){
        return ResponseEntity.ok(this.claseService.findAll(usuario));
    }

    @GetMapping("/{idClase}")
    public ResponseEntity<?> getClase(@PathVariable int idClase, @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(this.claseService.findById(idClase, usuario));
    }

    @PostMapping
    public ResponseEntity<?> createClaseAdmin(@RequestParam int idGrupo, @RequestParam int idUsuario,
                                              @RequestBody ClaseRequest claseRequest, @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(this.grupoService.createClaseAdmin(idGrupo, idUsuario, claseRequest, usuario));
    }

    @PutMapping("/{idClase}")
    public ResponseEntity<?> updateClaseAdmin(@PathVariable int idClase, @RequestParam int idGrupo, @RequestParam int idUsuario,
                                              @RequestBody ClaseRequest claseRequest, @AuthenticationPrincipal Usuario usuario){
        return ResponseEntity.ok(this.grupoService.updateClaseAdmin(idClase, idGrupo, idUsuario, claseRequest, usuario));
    }

    @DeleteMapping("/{idClase}")
    public ResponseEntity<?> deleteClase(@PathVariable int idClase, @AuthenticationPrincipal Usuario usuario) {
        this.claseService.deleteAdmin(idClase, usuario);
        return ResponseEntity.ok("Clase " + idClase + " eliminada con éxito.");
    }

}
