package com.miguel.web.controllers;

import com.miguel.persistence.entities.Usuario;
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
    public ResponseEntity<?> getAllAnotaciones(){
        return ResponseEntity.ok(this.anotacionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnotacion(@PathVariable int id){
        return ResponseEntity.ok(anotacionService.findByIdAdmin(id));
    }

    // Aux por grupoService
    @PostMapping
    public ResponseEntity<?> createAnotacionAdmin(@RequestParam int idGrupo, @RequestParam int idUsuario,
                                                  @RequestBody AnotacionDto anotacionRequest){
        return ResponseEntity.ok(this.grupoService.createAnotacionAdmin(idGrupo, idUsuario, anotacionRequest));
    }

    @PutMapping("/{idAnotacion}")
    public ResponseEntity<?> updateAnotacionAdmin(@PathVariable int idAnotacion,@RequestParam int idGrupo, @RequestParam int idUsuario,
                                                  @RequestBody AnotacionDto anotacionRequest){
        return ResponseEntity.ok(this.grupoService.updateAnotacionAdmin(idAnotacion, idGrupo, idUsuario, anotacionRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnotacion(@PathVariable int id){
        this.anotacionService.deleteAdmin(id);
        return ResponseEntity.ok("Anotación " + id + " eliminada con éxito.");
    }

}
