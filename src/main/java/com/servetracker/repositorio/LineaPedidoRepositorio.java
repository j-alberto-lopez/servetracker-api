package com.servetracker.repositorio;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.servetracker.modelo.LineaPedido;
import com.servetracker.modelo.Producto;

@Repository
public interface LineaPedidoRepositorio extends JpaRepository<LineaPedido, Integer>{
	List<LineaPedido> findByProductoAndCantidadDisponibleGreaterThan(Producto producto, int cantidad);
	
	// @Query("SELECT l FROM LineaPedido l WHERE l.producto = :producto AND l.cantidadDisponinble > 0 ")
	//List<LineaPedido> findDisponibleByProducto(Producto producto);

	// @Query("SELECT l FROM LineaPedido l WHERE l.producto = :producto AND l.cantidadDisponinble > 0 ORDER BY l.pedido.fecha ASC")
	//List<LineaPedido> findDisponibleByProductoOrdenadoPorFecha(Producto producto);


	List<LineaPedido> findByProductoAndCantidadDisponibleGreaterThanOrderByPedidoFechaAsc(Producto producto, int cantidad);
	//Stream<LineaPedido> findByProductoAndCantidadDisponibleGreaterThanOrderByPedidoFechaAsc(Producto producto, int cantidad);

	List<LineaPedido> findByProductoAndCantidadDisponibleGreaterThanOrderByPedidoFechaAsc(Producto producto, int cantidad, Pageable pageable);
	

}
