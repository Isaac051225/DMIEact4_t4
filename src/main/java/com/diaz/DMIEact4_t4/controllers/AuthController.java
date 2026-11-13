package com.diaz.DMIEact4_t4.controllers;

import com.diaz.DMIEact4_t4.Entity.Usuario;
import com.diaz.DMIEact4_t4.dto.AuthResponseDTO;
import com.diaz.DMIEact4_t4.dto.LoginRequestDTO;
import com.diaz.DMIEact4_t4.dto.RegisterRequestDTO;
import com.diaz.DMIEact4_t4.repository.UsuarioRepository;
import com.diaz.DMIEact4_t4.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/registro")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "El correo ya está registrado"));
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));

        usuarioRepository.save(usuario);
        String jwtToken = jwtService.generarToken(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                AuthResponseDTO.builder()
                        .token(jwtToken)
                        .email(usuario.getEmail())
                        .nombre(usuario.getNombre())
                        .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generarToken(usuario);

        return ResponseEntity.ok(
                AuthResponseDTO.builder()
                        .token(jwtToken)
                        .email(usuario.getEmail())
                        .nombre(usuario.getNombre())
                        .build()
        );
    }
}