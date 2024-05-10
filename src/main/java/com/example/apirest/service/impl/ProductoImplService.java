package com.example.apirest.service.impl;

import com.example.apirest.model.dto.ProductoDto;
import com.example.apirest.model.entity.Producto;
import com.example.apirest.repository.ProductoRepository;
import com.example.apirest.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoImplService implements IProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> listAll() {
        return (List) productoRepository.findAll();
    }

    @Transactional
    @Override
    public Producto save(ProductoDto productoDto) {
        Producto producto = Producto.builder()
                .id(productoDto.getId())
                .nombre(productoDto.getNombre())
                .descripcion(productoDto.getDescripcion())
                .precio(productoDto.getPrecio())
                .build();
        return productoRepository.save(producto);
    }

    @Transactional(readOnly = true)
    @Override
    public Producto findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Producto producto) {
        productoRepository.delete(producto);
    }

    @Override
    public boolean existsById(Long id) {
        return productoRepository.existsById(id);
    }
}
