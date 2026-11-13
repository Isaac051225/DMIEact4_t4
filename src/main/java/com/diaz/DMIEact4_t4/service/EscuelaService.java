package com.diaz.DMIEact4_t4.service;

import com.diaz.DMIEact4_t4.dto.EscuelaRequestDTO;
import com.diaz.DMIEact4_t4.dto.EscuelaResponseDTO;
import com.diaz.DMIEact4_t4.Entity.Escuela;
import com.diaz.DMIEact4_t4.repository.EscuelaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EscuelaService {

    private final EscuelaRepository escuelaRepository;

    // Listar con paginación
    public Page<EscuelaResponseDTO> listar(Pageable pageable) {
        return escuelaRepository.findAll(pageable).map(this::convertirADTO);
    }

    // Buscar una escuela por ID
    public Optional<EscuelaResponseDTO> buscarPorId(Long id) {
        return escuelaRepository.findById(id).map(this::convertirADTO);
    }

    // Crear una nueva escuela
    public EscuelaResponseDTO crear(EscuelaRequestDTO dto) {
        if (escuelaRepository.existsByClave(dto.getClave())) {
            throw new IllegalArgumentException("Ya existe una escuela con la clave: " + dto.getClave());
        }
        Escuela escuela = new Escuela();
        escuela.setNombre(dto.getNombre());
        escuela.setClave(dto.getClave());
        escuela.setDireccion(dto.getDireccion());

        Escuela guardada = escuelaRepository.save(escuela);
        return convertirADTO(guardada);
    }

    // Actualizar una escuela existente
    public Optional<EscuelaResponseDTO> actualizar(Long id, EscuelaRequestDTO dto) {
        return escuelaRepository.findById(id).map(escuela -> {
            if (!escuela.getClave().equals(dto.getClave()) && escuelaRepository.existsByClave(dto.getClave())) {
                throw new IllegalArgumentException("Ya existe otra escuela con la clave: " + dto.getClave());
            }
            escuela.setNombre(dto.getNombre());
            escuela.setClave(dto.getClave());
            escuela.setDireccion(dto.getDireccion());
            return convertirADTO(escuelaRepository.save(escuela));
        });
    }

    // Eliminar por ID
    public boolean eliminar(Long id) {
        if (escuelaRepository.existsById(id)) {
            escuelaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Método auxiliar privado para convertir Entity -> DTO
    private EscuelaResponseDTO convertirADTO(Escuela escuela) {
        return EscuelaResponseDTO.builder()
                .id(escuela.getId())
                .nombre(escuela.getNombre())
                .clave(escuela.getClave())
                .direccion(escuela.getDireccion())
                .build();
    }
}