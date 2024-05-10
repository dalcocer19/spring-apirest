package com.example.apirest.model.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@Builder
public class ProductoDto implements Serializable {

    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
}
