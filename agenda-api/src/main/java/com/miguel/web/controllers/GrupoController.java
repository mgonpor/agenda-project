package com.miguel.web.controllers;

import com.miguel.security.user.User;
import com.miguel.services.AnotacionService;
import com.miguel.services.GrupoService;
import com.miguel.services.dtos.AnotacionDto;
import com.miguel.services.dtos.GrupoRequest;
import com.miguel.services.dtos.GrupoResponse;
import com.miguel.services.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    //CRUDs
    @GetMapping
    public ResponseEntity<List<GrupoResponse>> findAll(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(grupoService.getGrupos(user.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id, @AuthenticationPrincipal User user){
        try{
            return ResponseEntity.ok(grupoService.getGrupo(id, user.getId()));
        }catch (GrupoNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody GrupoRequest grupoRequest, @AuthenticationPrincipal User user){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(grupoService.createGrupo(grupoRequest, user.getId()));
        }catch (GrupoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody GrupoRequest grupoRequest, @AuthenticationPrincipal User user){
        try {
            return ResponseEntity.ok(grupoService.updateGrupo(id, grupoRequest, user.getId()));
        }catch (GrupoNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (GrupoException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id, @AuthenticationPrincipal User user){
        try{
            return ResponseEntity.ok("Grupo " + grupoService.deleteGrupo(id, user.getId()) + " eliminado con éxito.");
        }catch (GrupoNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // OTROS
    @GetMapping("/search")
    public ResponseEntity<List<GrupoResponse>> findByNombre(@RequestParam String nombre, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(grupoService.getGruposByNombre(nombre, user.getId()));
    }

    // todo: pasar a AnotacionController
    @PostMapping("/{idGrupo}/anotaciones")
    public ResponseEntity<?> createAnotacion(@PathVariable int idGrupo, @RequestBody AnotacionDto anotacionRequest, @AuthenticationPrincipal User user){
        try{
            if (!grupoService.perteneceAUsuario(idGrupo, user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(anotacionService.createAnotacion(idGrupo, anotacionRequest, user.getId()));
        }catch (GrupoNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (AnotacionException | EmptyTextException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{idGrupo}/anotaciones/{idAnotacion}")
    public ResponseEntity<?> updateAnotacion(@PathVariable int idGrupo, @PathVariable int idAnotacion, @RequestBody AnotacionDto anotacionRequest, @AuthenticationPrincipal User user){
        try{
            return ResponseEntity.ok(anotacionService.updateAnotacion(idGrupo, idAnotacion, anotacionRequest, user.getId()));
        }catch (GrupoNotFoundException | AnotacionNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (AnotacionException | EmptyTextException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{idGrupo}/anotaciones/{idAnotacion}")
    public ResponseEntity<?> deleteAnotacion(@PathVariable int idGrupo, @PathVariable int idAnotacion, @AuthenticationPrincipal User user){
        try{
            anotacionService.delete(idGrupo, idAnotacion, user.getId());
            return ResponseEntity.ok("Anotación eliminada con éxito.");
        }catch (GrupoNotFoundException | AnotacionNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (WrongUserException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
