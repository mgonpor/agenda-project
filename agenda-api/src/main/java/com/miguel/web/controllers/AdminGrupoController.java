package com.miguel.web.controllers;

import com.miguel.persistence.entities.Usuario;
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
    public ResponseEntity<?> findAllAdmin(){
        return ResponseEntity.ok(this.grupoService.getAllGruposAdmin());
    }

    @GetMapping("/{idGrupo}")
    public ResponseEntity<?> findByIdAdmin(@PathVariable int idGrupo){
        return ResponseEntity.ok(this.grupoService.getGrupoAdmin(idGrupo));
    }

    @PostMapping
    public ResponseEntity<?> createAdmin(@RequestBody GrupoRequest grupoRequest, @RequestParam int idUsuario){
        return ResponseEntity.ok(this.grupoService.createGrupoAdmin(grupoRequest, idUsuario));
    }

    @PutMapping("/{idGrupo}")
    public ResponseEntity<?> updateAdmin(@PathVariable int idGrupo, @RequestBody GrupoRequest grupoRequest,
                                         @RequestParam int idUsuario){
        return ResponseEntity.ok(this.grupoService.updateGrupoAdmin(idGrupo, grupoRequest, idUsuario));
    }

    @DeleteMapping("/{idGrupo}")
    public ResponseEntity<?> deleteAdmin(@PathVariable int idGrupo){
        return ResponseEntity.ok("Grupo " + grupoService.deleteGrupoAdmin(idGrupo)
                + " eliminado con éxito.");
    }

}
