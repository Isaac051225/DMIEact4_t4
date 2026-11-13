package com.diaz.DMIEact4_t4.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EscuelaRequestDTO {

    @NotBlank(message = "El nombre de la escuela es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "La clave de la escuela es obligatoria")
    @Size(min = 3, max = 20, message = "La clave debe tener entre 3 y 20 caracteres")
    private String clave;

    @Size(max = 150, message = "La dirección no puede exceder los 150 caracteres")
    private String direccion;
}