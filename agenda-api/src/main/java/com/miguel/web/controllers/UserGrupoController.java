package com.miguel.web.controllers;

import com.miguel.persistence.entities.Usuario;
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
    public ResponseEntity<List<GrupoResponse>> findAll(){
        return ResponseEntity.ok(grupoService.getGruposUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        return ResponseEntity.ok(grupoService.getGrupoUser(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody GrupoRequest grupoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(grupoService.createGrupoUser(grupoRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody GrupoRequest grupoRequest){
        return ResponseEntity.ok(grupoService.updateGrupoUser(id, grupoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        return ResponseEntity.ok("Grupo " + grupoService.deleteGrupoUser(id) + " eliminado con éxito.");
    }

}
