package com.servetracker.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servetracker.modelo.Producto;

@Repository
public interface ProductoCliRepositorio extends JpaRepository<Producto, Integer> {

    // 🔹 Productos con stock > 0
    List<Producto> findByStockActualGreaterThan(double stock);

}