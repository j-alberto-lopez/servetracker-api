package com.servetracker.servicio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servetracker.dtos.ElementoListadoProductoRespuesta;
import com.servetracker.dtos.ProveedorStockDTO;
import com.servetracker.excepciones.NoEncontradoException;
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

        List<Producto> productos = productoRepository.findAll();
        List<ElementoListadoProductoRespuesta> resultado = new ArrayList<>();

        LocalDate ahora = LocalDate.now();
        LocalDate hace7dias = ahora.minusDays(7);
        LocalDate hace14dias = ahora.minusDays(14);

        for (Producto producto : productos) {

            // 🔹 Ventas
            int ventasSemanales = calcularVentasSemana(producto, hace7dias, ahora);
            int ventasSemanaOld = calcularVentasSemana(producto, hace14dias, hace7dias);
            int estadoVentas = ventasSemanales - ventasSemanaOld;

            // 🔹 Proveedores
            List<ProveedorStockDTO> proveedores = obtenerProveedores(producto);

            // 🔹 Precio compra (media ponderada)
            double precioCompra = calcularPrecioCompra(producto);

            // 🔹 Beneficio (FIFO)
            double beneficio = calcularBeneficio(producto);
            double beneficioSemana = calcularBeneficioSemana(producto, hace7dias, ahora);
            double beneficioSemanaOld = calcularBeneficioSemana(producto, hace14dias, hace7dias);

            double estadoBeneficioSemana = beneficioSemana - beneficioSemanaOld;

            // 🔹 Datos básicos
            ElementoListadoProductoRespuesta dto = new ElementoListadoProductoRespuesta();

            dto.setNombre(producto.getNombre());
            dto.setPrecioVenta(producto.getPrecioVenta());
            dto.setTipo(producto.getTipo().name());
            dto.setPrecioCompra(precioCompra);
            dto.setProveedores(proveedores);
            dto.setVentasSemanales(ventasSemanales);
            dto.setEstadoVentas(estadoVentas);
            dto.setBeneficioProducto(beneficio);
            dto.setBeneficioSemana(beneficioSemana);
            dto.setEstadoBeneficioSemana(estadoBeneficioSemana);
           

            resultado.add(dto);
        }

        return resultado;
    }

    // =====================================================
    // 🔹 MÉTODOS PRIVADOS (LÓGICA SEPARADA)
    // =====================================================

    private int calcularVentasSemana(Producto producto, LocalDate inicio, LocalDate fin) {
        List<LineaTicket> lineas = lineaTicketRepositorio
                .findByProductoAndTicketFechaGreaterThanEqualAndTicketFechaLessThan(producto, inicio, fin);

        int total = 0;
        for (LineaTicket lt : lineas) {
            total += lt.getCantidad();
        }
        return total;
    }

    private List<ProveedorStockDTO> obtenerProveedores(Producto producto) {

        List<ProveedorStockDTO> lista = new ArrayList<>();

        List<LineaPedido> lineas = lineaPedidoRepositorio
                .findByProductoAndCantidadDisponibleGreaterThanOrderByPedidoFechaAsc(producto, 0);

        for (LineaPedido l : lineas) {
            String nombreProveedor = l.getPedido().getProveedor().getNombre();
            double cantidad = l.getCantidadDisponible();

            lista.add(new ProveedorStockDTO(nombreProveedor, cantidad));
        }

        return lista;
    }

    private double calcularPrecioCompra(Producto producto) {

        List<LineaPedido> lineas = lineaPedidoRepositorio
                .findByProductoAndCantidadDisponibleGreaterThanOrderByPedidoFechaAsc(producto, 0);

        BigDecimal totalCoste = BigDecimal.ZERO;
        double totalUnidades = 0;

        for (LineaPedido lp : lineas) {
            double cantidad = lp.getCantidadDisponible();
            BigDecimal precio = lp.getPrecioUnitario();

            BigDecimal coste = precio.multiply(BigDecimal.valueOf(cantidad));
            totalCoste = totalCoste.add(coste);

            totalUnidades += cantidad;
        }

        if (totalUnidades == 0) return 0;

        return totalCoste
                .divide(BigDecimal.valueOf(totalUnidades), 2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private double calcularBeneficio(Producto producto) {

        List<LineaPedido> lineas = lineaPedidoRepositorio
                .findByProductoAndCantidadDisponibleGreaterThanOrderByPedidoFechaAsc(producto, 0);

        double stockActual = producto.getStockActual();
        double costeTotal = 0;

        for (LineaPedido lp : lineas) {

            if (stockActual <= 0) break;

            double disponible = lp.getCantidadDisponible();

            if (disponible <= 0) continue;

            if (stockActual >= disponible) {
                costeTotal += disponible * lp.getPrecioUnitario().doubleValue();
                stockActual -= disponible;
            } else {
                costeTotal += stockActual * lp.getPrecioUnitario().doubleValue();
                stockActual = 0;
            }
        }

        if (producto.getStockActual() == 0) return 0;

        double costeUnitario = costeTotal / producto.getStockActual();

        return producto.getPrecioVenta() - costeUnitario;
    }
    private double calcularBeneficioSemana(Producto producto, LocalDate inicio, LocalDate fin) {

        List<LineaTicket> lineas = lineaTicketRepositorio
                .findByProductoAndTicketFechaGreaterThanEqualAndTicketFechaLessThan(producto, inicio, fin);

        double total = 0;

        for (LineaTicket lt : lineas) {
            total += lt.getBeneficioUnitarioEnVenta() * lt.getCantidad();
        }

        return total;
    }

    // =====================================================
    // 🔹 CRUD
    // =====================================================

    @Override
    public Producto obtenerPorId(int id) throws NoEncontradoException {
        return productoRepository.findById(id)
                .orElseThrow(() -> new NoEncontradoException());
    }

    @Override
    public Producto guardar(Producto producto) {

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


/*package com.servetracker.servicio;

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
		 * vendido entre 7 y 14 dias antes. 
		 * - beneficioProducto; -
		 * estadoBeneficioProducto; // número (ej: beneficio €)
		 * 
		 * -beneficioSemana; - estadoBeneficioSemana;
		 */

		// 🔹 1. Obtener todos los productos
	/*	List<Producto> productos = productoRepository.findAll();

		// 🔹 Lista final que devolverás al frontend
		List<ElementoListadoProductoRespuesta> resultado = new ArrayList<>();

		// 🔹 2. Recorrer cada producto (todo se calcula por producto)
		
		
		//Ventas semana
		LocalDateTime ahora = LocalDateTime.now();
		LocalDateTime hace7dias = ahora.minusDays(7);
		LocalDateTime hace14dias = ahora.minusDays(14);
		
		
		for (Producto producto : productos) {
			
			// traer SOLO ventas de ese producto en ese rango"Incluye inicio , excluye final"
			List<LineaTicket> lineasTicket =
				    lineaTicketRepositorio.findByProductoAndTicketFechaGreaterThanEqualAndTicketFechaLessThan(producto, hace7dias, ahora);
			//Semena anterior
			List<LineaTicket> lineasTicketOld = 
					lineaTicketRepositorio.findByProductoAndTicketFechaGreaterThanEqualAndTicketFechaLessThan(producto, hace14dias,hace7dias);

			//  sumar unidades
			int ventasSemanales = 0;
			int ventasSemanaOld = 0;
			int estadoVentas = ventasSemanales - ventasSemanaOld;

			for (LineaTicket lt : lineasTicket) {
			    ventasSemanales += lt.getCantidad();
			}
			for (LineaTicket lt1 : lineasTicketOld) {
			    ventasSemanaOld += lt1.getCantidad();
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
				
				/*
				  double precioCompra = calcularPrecioCompra(producto);
				  double beneficio = calcularBeneficio(producto);
				  int ventasSemanales = calcularVentasSemana(producto);
				  int estadoVentas = calcularEstadoVentas(producto);
				  */

	/*			double cantidadDisponible = lineaPedido.getCantidadDisponible();
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
			productoRespuesta.setEstadoVentas(estadoVentas);

			resultado.add(productoRespuesta);
		}
		// 🔹 devolver lista completa
		return resultado;
	}

	//METODOS PRIVADOS
	private int calcularVentasSemana(Producto producto, LocalDateTime inicio, LocalDateTime fin) {
        List<LineaTicket> lineas = lineaTicketRepositorio
                .findByProductoAndTicketFechaGreaterThanEqualAndTicketFechaLessThan(producto, inicio, fin);

        int total = 0;
        for (LineaTicket lt : lineas) {
            total += lt.getCantidad();
        }
        return total;
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
}*/
