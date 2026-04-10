package com.servetracker.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servetracker.modelo.Producto;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Integer> {

	List<Producto> findByStockActualGreaterThan(double cantidad);
}
