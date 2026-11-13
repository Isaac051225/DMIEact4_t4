package com.diaz.DMIEact4_t4.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EscuelaResponseDTO {
    private Long id;
    private String nombre;
    private String clave;
    private String direccion;
}