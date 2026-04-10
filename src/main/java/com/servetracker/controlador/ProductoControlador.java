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

import com.servetracker.modelo.Producto;
import com.servetracker.servicio.ProductoServicio;



@RestController
@RequestMapping("/api/v1/productos")
public class ProductoControlador {

    @Autowired
    private ProductoServicio servicio;

    
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodos() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Dificultad> obtenerPorId(@PathVariable int id) {

        Dificultad dificultad = servicio.obtenerPorId(id);

        if (dificultad == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dificultad);
    }

    
    @PostMapping
    public ResponseEntity<Dificultad> guardar(@RequestBody Dificultad dificultad) {
        return ResponseEntity.ok(servicio.guardar(dificultad));
    }

   
    @PutMapping("/{id}")
    public ResponseEntity<Dificultad> modificar(@PathVariable int id, @RequestBody Dificultad dificultad) {
        dificultad.setId(id);
        return ResponseEntity.ok(servicio.guardar(dificultad));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable int id) {
        servicio.borrar(id);
        return ResponseEntity.noContent().build();
    }
}