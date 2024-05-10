package com.example.apirest.controller;

import com.example.apirest.model.dto.ProductoDto;
import com.example.apirest.model.entity.Producto;
import com.example.apirest.model.payload.MessageResponse;
import com.example.apirest.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
/*import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;*/
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ProductoController /*extends WebSecurityConfigurerAdapter*/{

    @Autowired
    private IProductoService productoService;

    @PostMapping("producto")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createProducto(@RequestBody ProductoDto productoDto) {
        Producto productoSave = null;
        try {
            productoSave = productoService.save(productoDto);
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Guardado correctamente")
                    .object(ProductoDto.builder()
                            .id(productoSave.getId())
                            .nombre(productoSave.getNombre() )
                            .descripcion(productoSave.getDescripcion())
                            .precio(productoSave.getPrecio())
                            .build())
                    .build()
                    , HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PutMapping("producto/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> updateProducto(@RequestBody ProductoDto productoDto, @PathVariable Long id) {
        Producto productoUpdate = null;
        try {
            if (productoService.existsById(id)) {
                productoDto.setId(id);
                productoUpdate = productoService.save(productoDto);
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Guardado correctamente")
                        .object(ProductoDto.builder()
                                .id(productoUpdate.getId())
                                .nombre(productoUpdate.getNombre())
                                .descripcion(productoUpdate.getDescripcion())
                                .precio(productoUpdate.getPrecio())
                                .build())
                        .build()
                        , HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        MessageResponse.builder()
                                .message("El registro que intenta actualizar no se encuentra en la base de datos.")
                                .object(null)
                                .build()
                        , HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("producto/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable Long id) {
        try{
            Producto p = productoService.findById(id);
            productoService.delete(p);
            return new ResponseEntity<>(p, HttpStatus.OK);
        }catch (DataAccessException exDt){
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("producto/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        if (producto == null) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message("El registro que intenta buscar, no existe!!")
                            .object(null)
                            .build()
                    , HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                MessageResponse.builder()
                        .message("")
                        .object(ProductoDto.builder()
                                .id(producto.getId())
                                .nombre(producto.getNombre())
                                .descripcion(producto.getDescripcion())
                                .precio(producto.getPrecio())
                                .build())
                        .build()
                , HttpStatus.OK);
    }

    @GetMapping("productos")
    public ResponseEntity<?> findAll() {
        List<Producto> getList= productoService.listAll();
        if(getList == null){
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message("No hay registros")
                            .object(null)
                            .build()
                    , HttpStatus.OK);
        }

        return new ResponseEntity<>(
                MessageResponse.builder()
                        .message("")
                        .object(getList)
                        .build()
                , HttpStatus.OK);
    }

    /*@RequestMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }*/

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeRequests(a -> a
                        .antMatchers("/", "/error", "/webjars/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .csrf(c -> c
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .logout(l -> l
                        .logoutSuccessUrl("/").permitAll()
                )
                .oauth2Login();
        // @formatter:on
    }*/
}
