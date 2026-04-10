package com.servetracker.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servetracker.modelo.Producto;
import com.servetracker.repositorio.ProductoRepositorio;

@Service
public class ProductoServicioImpl implements ProductoServicio {

    @Autowired
    private ProductoRepositorio productoRepository;

    @Override
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto obtenerPorId(int id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }

    @Override
    public Producto guardar(Producto producto) {

        // 🔥 VALIDACIONES PRO
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre del producto es obligatorio");
        }

        if (producto.getPrecioVenta() <= 0) {
            throw new RuntimeException("El precio debe ser mayor que 0");
        }

        if (producto.getStockActual() < 0) {
            throw new RuntimeException("El stock no puede ser negativo");
        }

        return productoRepository.save(producto);
    }

    @Override
    public void eliminar(int id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Producto no existe");
        }
        productoRepository.deleteById(id);
    }

    @Override
    public List<Producto> obtenerConStockActual() {
        return productoRepository.findByStockGreaterThan(0);
    }
}