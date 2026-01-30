package com.miguel.web.controllers;

import com.miguel.persistence.entities.user.User;
import com.miguel.services.GrupoService;
import com.miguel.services.dtos.GrupoRequest;
import com.miguel.services.dtos.GrupoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/grupo")
public class UserGrupoController {

    @Autowired
    private GrupoService grupoService;

    //CRUDs
    @GetMapping
    public ResponseEntity<List<GrupoResponse>> findAll(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(grupoService.getGrupos(user.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(grupoService.getGrupo(id, user.getId()));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody GrupoRequest grupoRequest, @AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(grupoService.createGrupo(grupoRequest, user.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody GrupoRequest grupoRequest, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(grupoService.updateGrupo(id, grupoRequest, user.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id, @AuthenticationPrincipal User user){
        return ResponseEntity.ok("Grupo " + grupoService.deleteGrupo(id, user.getId()) + " eliminado con éxito.");
    }
    // OTROS
    @GetMapping("/search")
    public ResponseEntity<List<GrupoResponse>> findByNombre(@RequestParam String nombre, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(grupoService.getGruposByNombre(nombre, user.getId()));
    }

}
