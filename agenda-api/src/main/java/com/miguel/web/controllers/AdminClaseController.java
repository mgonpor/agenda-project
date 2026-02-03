package com.miguel.web.controllers;

import com.miguel.services.ClaseService;
import com.miguel.services.GrupoService;
import com.miguel.services.dtos.ClaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getClases() {
        return ResponseEntity.ok(this.claseService.findAllAdmin());
    }

    @GetMapping("/{idClase}")
    public ResponseEntity<?> getClase(@PathVariable int idClase) {
        return ResponseEntity.ok(this.claseService.findByIdAdmin(idClase));
    }

    @PostMapping
    public ResponseEntity<?> createClaseAdmin(@RequestParam int idGrupo, @RequestParam int idUsuario,
                                              @RequestBody ClaseRequest claseRequest) {
        return ResponseEntity.ok(this.grupoService.createClaseAdmin(idGrupo, idUsuario, claseRequest));
    }

    @PutMapping("/{idClase}")
    public ResponseEntity<?> updateClaseAdmin(@PathVariable int idClase, @RequestParam int idGrupo, @RequestParam int idUsuario,
                                              @RequestBody ClaseRequest claseRequest){
        return ResponseEntity.ok(this.grupoService.updateClaseAdmin(idClase, idGrupo, idUsuario, claseRequest));
    }

    @DeleteMapping("/{idClase}")
    public ResponseEntity<?> deleteClase(@PathVariable int idClase) {
        this.claseService.deleteAdmin(idClase);
        return ResponseEntity.ok("Clase " + idClase + " eliminada con éxito.");
    }

}
