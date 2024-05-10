package com.example.apirest.repository;

import com.example.apirest.model.entity.Producto;
import org.springframework.data.repository.CrudRepository;

public interface ProductoRepository extends CrudRepository<Producto, Long> {

}
