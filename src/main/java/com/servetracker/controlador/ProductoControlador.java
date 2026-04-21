package com.servetracker.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servetracker.dtos.ElementoListadoProductoRespuesta;
import com.servetracker.modelo.Producto;
import com.servetracker.servicio.ProductoServicio;



@RestController
@RequestMapping("/api/v1/productos")
public class ProductoControlador {

    @Autowired
    private ProductoServicio servicio;

    
    @GetMapping
    public ResponseEntity<List<ElementoListadoProductoRespuesta>> obtenerTodos() {
        return ResponseEntity.ok(servicio.obtenerListado());
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable int id) {

        Producto producto = servicio.obtenerPorId(id);

        if (producto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(producto);
    }

    
    @PostMapping
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto) {
        return ResponseEntity.ok(servicio.guardar(producto));
    }

   
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable int id, @RequestBody Producto producto) {
        producto.setId(id);
        return ResponseEntity.ok(servicio.guardar(producto));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/con-stock")
    public ResponseEntity<List<Producto>> obtenerConStock() {
        return ResponseEntity.ok(servicio.obtenerConStockActual());
    }
}