package com.diaz.DMIEact4_t4.controllers;

import com.diaz.DMIEact4_t4.dto.EscuelaRequestDTO;
import com.diaz.DMIEact4_t4.dto.EscuelaResponseDTO;
import com.diaz.DMIEact4_t4.service.EscuelaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/escuelas")
@RequiredArgsConstructor
public class EscuelaController {

    private final EscuelaService escuelaService;

    // 1. Listar escuelas con paginación (GET /api/escuelas?page=0&size=5)
    @GetMapping
    public ResponseEntity<Page<EscuelaResponseDTO>> listar(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(escuelaService.listar(pageable));
    }

    // 2. Obtener una escuela por ID (GET /api/escuelas/{id})
    @GetMapping("/{id}")
    public ResponseEntity<EscuelaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return escuelaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Crear una nueva escuela (POST /api/escuelas)
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody EscuelaRequestDTO dto) {
        try {
            EscuelaResponseDTO creada = escuelaService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // 4. Actualizar una escuela existente (PUT /api/escuelas/{id})
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody EscuelaRequestDTO dto) {
        try {
            return escuelaService.actualizar(id, dto)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // 5. Eliminar una escuela (DELETE /api/escuelas/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (escuelaService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}