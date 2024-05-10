package com.example.apirest.service;

import com.example.apirest.model.dto.ProductoDto;
import com.example.apirest.model.entity.Producto;

import java.util.List;

public interface IProductoService {
    List<Producto> listAll();
    Producto save(ProductoDto producto);
    Producto findById(Long id);
    void delete(Producto producto);
    boolean existsById(Long id);
}
