package com.servetracker.servicio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.servetracker.dtos.ElementoListadoProductoRespuesta;
import com.servetracker.dtos.ProveedorStockDTO;
import com.servetracker.modelo.LineaPedido;
import com.servetracker.modelo.LineaTicket;
import com.servetracker.modelo.Producto;
import com.servetracker.repositorio.LineaPedidoRepositorio;
import com.servetracker.repositorio.LineaTicketRepositorio;
import com.servetracker.repositorio.ProductoRepositorio;

@Service
public class ProductoServicioImpl implements ProductoServicio {

	@Autowired
	private ProductoRepositorio productoRepository;
	@Autowired
	private LineaPedidoRepositorio lineaPedidoRepositorio;
	@Autowired
	private LineaTicketRepositorio lineaTicketRepositorio;

	@Override
	public List<ElementoListadoProductoRespuesta> obtenerListado() {
		/*
		 * Obtener todos los productos, y para cada producto calcular: - precioCompra:
		 * Media ponderada de los precios de compra de todos los productos para los que
		 * haya unidades disponibles. - ventasSemanales: Total de ventas (sacadas de
		 * linea ticket) en los últimos 7 dias. - estadoVentasSemanales: Relacion entre
		 * el numero de ventas en los ultimos 7 dias / el numero de ventas entre lo
		 * vendido entre 7 y 14 dias antes. - beneficioProducto; -
		 * estadoBeneficioProducto; // número (ej: beneficio €)
		 * 
		 * -beneficioSemana; - estadoBeneficioSemana;
		 */

		// 🔹 1. Obtener todos los productos
		List<Producto> productos = productoRepository.findAll();

		// 🔹 Lista final que devolverás al frontend
		List<ElementoListadoProductoRespuesta> resultado = new ArrayList<>();

		// 🔹 2. Recorrer cada producto (todo se calcula por producto)
		
		
		//Ventas semana
		LocalDateTime ahora = LocalDateTime.now();
		LocalDateTime hace7dias = ahora.minusDays(7);
		for (Producto producto : productos) {
			
			// traer SOLO ventas de ese producto en ese rango
			List<LineaTicket> lineasTicket =
				    lineaTicketRepositorio.findByProductoAndTicketFechaBetween(producto, hace7dias, ahora);

			//  sumar unidades
			int ventasSemanales = 0;

			for (LineaTicket lt : lineasTicket) {
			    ventasSemanales += lt.getCantidad();
			}
		
			// =====================================================
			// 🔸 BLOQUE PROVEEDORES (detalle, NO agrupado)
			// =====================================================

			// Lista de proveedores para ESTE producto
			List<ProveedorStockDTO> proveedoresCantidad = new ArrayList<>();

			// Obtener líneas de pedido (compras) con stock disponible
			List<LineaPedido> lineasPedido = lineaPedidoRepositorio
					.findByProductoAndCantidadDisponibleGreaterThanOrderByPedidoFechaAsc(producto, 0);
			// Recorrer cada línea de pedido para sacar proveedor + cantidad
			for (LineaPedido l : lineasPedido) {
				String nombreProveedor = l.getPedido().getProveedor().getNombre();
				double cantidad = l.getCantidadDisponible();
				// DTO con proveedor y cantidad de ese pedido
				ProveedorStockDTO dto = new ProveedorStockDTO(nombreProveedor, cantidad);

				proveedoresCantidad.add(dto);
			}

			// =====================================================
			// 🔸 BLOQUE PRECIO COMPRA (MEDIA PONDERADA)
			// =====================================================

			// Acumuladores
			BigDecimal totalCoste = BigDecimal.ZERO;
			double totalUnidades = 0;

			// Recorrer líneas de pedido para calcular coste total
			for (LineaPedido lineaPedido : lineasPedido) {

				double cantidadDisponible = lineaPedido.getCantidadDisponible();
				BigDecimal precioUnitario = lineaPedido.getPrecioUnitario();

				// Convertir a BigDecimal para operar correctamente

				BigDecimal cantidad = BigDecimal.valueOf(cantidadDisponible);

				// coste = precio * cantidad
				BigDecimal costeLinea = precioUnitario.multiply(cantidad);
				// acumular
				totalCoste = totalCoste.add(costeLinea);

				totalUnidades += cantidadDisponible;
			}
			// calcular media ponderada
			BigDecimal precioCompraBD = BigDecimal.ZERO;

			if (totalUnidades > 0) {
				precioCompraBD = totalCoste.divide(BigDecimal.valueOf(totalUnidades), 2, RoundingMode.HALF_UP);
			}

			double precioCompra = precioCompraBD.doubleValue();

			// =====================================================
			// 🔸 DATOS BÁSICOS DEL PRODUCTO
			// =====================================================

			String nombre = producto.getNombre();
			double precioVenta = producto.getPrecioVenta();
			String tipo = producto.getTipo().name();

			// =====================================================
			// 🔸 CREACIÓN DEL DTO (LO QUE DEVUELVES)
			// =====================================================

			ElementoListadoProductoRespuesta productoRespuesta = new ElementoListadoProductoRespuesta();

			productoRespuesta.setNombre(nombre);
			productoRespuesta.setPrecioVenta(precioVenta);
			productoRespuesta.setTipo(tipo);
			productoRespuesta.setPrecioCompra(precioCompra);
			productoRespuesta.setProveedores(proveedoresCantidad);
			productoRespuesta.setVentasSemanales(ventasSemanales);

			resultado.add(productoRespuesta);
		}
		// 🔹 devolver lista completa
		return resultado;
	}

	@Override
	public Producto obtenerPorId(int id) {
		return productoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
	}

	@Override
	public Producto guardar(Producto producto) {

		// VALIDACIONES PRO
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
		return productoRepository.findByStockActualGreaterThan(0);
	}
}
