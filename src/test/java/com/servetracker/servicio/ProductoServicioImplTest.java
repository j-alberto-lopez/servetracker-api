package com.servetracker.servicio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.servetracker.excepciones.NoEncontradoException;
import com.servetracker.modelo.Producto;
import com.servetracker.modelo.TipoProducto;
import com.servetracker.repositorio.ProductoRepositorio;

@ExtendWith(MockitoExtension.class)
public class ProductoServicioImplTest {

	@Mock
	private ProductoRepositorio pr;
	
	@InjectMocks
	private ProductoServicioImpl ps;


	@Test
	public void testObtenerProductoPorIdConIdValido() throws NoEncontradoException {
		
		Producto p = new Producto(1, "prueba", TipoProducto.BEBIDA, 1, 1, 1, null, null);
		
		when(pr.findById(1)).thenReturn(Optional.of(p));
		
		Producto result = ps.obtenerPorId(1);
				
		assertNotNull(result);
		assertEquals("prueba", result.getNombre());		
	}
	
	@Test
	public void testObtenerProductoPoIdConIdNoExistente() {
		when(pr.findById(1)).thenReturn(Optional.empty());
		
		assertThrows(NoEncontradoException.class, () -> {
			ps.obtenerPorId(1);
		});
		
	}
	
	// Probar insertar producto con nombre a null o vacio
	@Test
	public void testInsertarProductoConNombreVacio() throws IllegalArgumentException {
		
		Producto p = new Producto(1, "", TipoProducto.BEBIDA, 1, 1, 1, null, null);
		
		assertThrows(IllegalArgumentException.class, ()->{
		ps.guardar(p);
		});
	}
	@Test
	public void testInsertarProductoConNombreNull() throws IllegalArgumentException {
		
		Producto p = new Producto(1, "", TipoProducto.BEBIDA, 1, 1, 1, null, null);
		
		assertThrows(IllegalArgumentException.class, ()->{
		ps.guardar(p);
		});
	}
	// Probar insertar poducto con precio venta 0 o negativo
	@Test
	public void testInsertarProductoConPrecioVenta0() throws IllegalArgumentException {
		
		Producto p = new Producto(1, "prueba", TipoProducto.BEBIDA, 0, 1, 1, null, null);
		
		assertThrows(IllegalArgumentException.class, ()->{
		ps.guardar(p);
		});
	}
	
	@Test
	public void testInsertarProductoConPrecioVentaNegativo() throws IllegalArgumentException {
		
		Producto p = new Producto(1, "prueba", TipoProducto.BEBIDA, -5, 1, 1, null, null);
		
		assertThrows(IllegalArgumentException.class, ()->{
		ps.guardar(p);
		});
	}
	// Probar insertar producto con stock negativo
	@Test
	public void testInsertarProductoConStockNegativo() throws IllegalArgumentException {
		
		Producto p = new Producto(1, "prueba", TipoProducto.BEBIDA, 1, -5, 1, null, null);
		
		assertThrows(IllegalArgumentException.class, ()->{
		ps.guardar(p);
		});
	}
	// Probar insertar producto correcto (stock 0)
	@Test
	public void testInsertarProductoConStock0(){
		
		Producto p = new Producto(1, "prueba", TipoProducto.BEBIDA, 1, 0, 1, null, null);
		
		when(pr.save(p)).thenReturn(p);
		
		Producto result =  ps.guardar(p);
		
		 assertNotNull(result);
		 assertEquals(0, result.getStockActual());
	}
	// Probar insertar producto correcto
	
	@Test
	public void testInsertarProductoCorrecto() {

	    Producto p = new Producto(1, "prueba", TipoProducto.BEBIDA, 1, 1, 1, null, null);

	    when(pr.save(p)).thenReturn(p);

	    Producto result = ps.guardar(p);

	    assertNotNull(result);
	    assertEquals("prueba", result.getNombre());
	}
}
