package com.miguel.web.controllers;

import com.miguel.services.UsuarioService;
import com.miguel.services.dtos.LoginRequest;
import com.miguel.services.dtos.LoginResponse;
import com.miguel.services.dtos.RefreshDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(this.usuarioService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest request) {
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, this.usuarioService.registrar(request)).build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshDTO request) {
        return ResponseEntity.ok(this.usuarioService.refresh(request));
    }

}
