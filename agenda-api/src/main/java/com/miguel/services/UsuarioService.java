package com.miguel.services;

import com.miguel.services.dtos.LoginResponse;
import com.miguel.persistence.entities.Usuario;
import com.miguel.persistence.repositories.UsuarioRepository;
import com.miguel.services.dtos.RefreshDTO;
import com.miguel.web.config.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtils jwtUtil;

    // AuthenticationManager REMOVED - this fixes the circular dependency

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = this.usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe. "));

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRol())
                .build();
    }

    public Usuario create(String username, String password) {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol("USER");
        return usuarioRepository.save(usuario);
    }

    public LoginResponse refresh(RefreshDTO dto) {
        String accessToken = jwtUtil.generateAccessToken(dto.getRefresh());
        String refreshToken = jwtUtil.generateRefreshToken(dto.getRefresh());

        return new LoginResponse(accessToken, refreshToken);
    }

    // Helper methods for token generation (used by controller)
    public String generateAccessToken(UserDetails userDetails) {
        return jwtUtil.generateAccessToken(userDetails);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return jwtUtil.generateRefreshToken(userDetails);
    }

}
