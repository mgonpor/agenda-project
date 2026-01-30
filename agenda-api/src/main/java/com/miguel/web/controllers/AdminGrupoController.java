package com.miguel.web.controllers;

import com.miguel.persistence.entities.user.User;
import com.miguel.services.GrupoService;
import com.miguel.services.dtos.GrupoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/grupo")
public class AdminGrupoController {

    @Autowired
    private GrupoService grupoService;

    // CRUDs ADMIN
    @GetMapping
    public ResponseEntity<?> findAllAdmin(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(this.grupoService.getGruposAdmin(user));
    }

    @GetMapping("/{idGrupo}")
    public ResponseEntity<?> findByIdAdmin(@PathVariable int idGrupo, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(this.grupoService.findByIdAdmin(idGrupo, user));
    }

    @PostMapping
    public ResponseEntity<?> createAdmin(@RequestBody GrupoRequest grupoRequest, @RequestParam int idUsuario,
                                         @AuthenticationPrincipal User user){
        return ResponseEntity.ok(this.grupoService.createAdmin(grupoRequest, idUsuario, user));
    }

    @PutMapping("/{idGrupo}")
    public ResponseEntity<?> updateAdmin(@PathVariable int idGrupo, @RequestBody GrupoRequest grupoRequest,
                                         @RequestParam int idUsuario, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(this.grupoService.updateAdmin(idGrupo, grupoRequest, idUsuario, user));
    }

    @DeleteMapping("/{idGrupo}")
    public ResponseEntity<?> deleteAdmin(@PathVariable int idGrupo, @RequestParam int idUsuario,
                                         @AuthenticationPrincipal User user){
        return ResponseEntity.ok("Grupo " + grupoService.deleteAdmin(idGrupo, idUsuario, user)
                + " eliminado con éxito.");
    }

}
