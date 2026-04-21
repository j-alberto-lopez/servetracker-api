package com.servetracker.servicio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servetracker.dtos.ElementoListadoProductoRespuesta;
import com.servetracker.modelo.LineaPedido;
import com.servetracker.modelo.Producto;
import com.servetracker.repositorio.LineaPedidoRepositorio;
import com.servetracker.repositorio.ProductoRepositorio;

@Service
public class ProductoServicioImpl implements ProductoServicio {

    @Autowired
    private ProductoRepositorio productoRepository;
    @Autowired
    private LineaPedidoRepositorio lineaPedidoRepositorio;
    
    

    @Override
    public List<ElementoListadoProductoRespuesta> obtenerListado() {
    	/*
    	 * Obtener todos los productos, y para cada producto calcular:
    	 * - precioCompra: Media ponderada de los precios de compra de todos los productos 
    	 * para los que haya unidades disponibles.
    	 * - ventasSemanales: Total de ventas (sacadas de linea ticket) en los últimos 7 dias.
    	 * - estadoVentasSemanales: Relacion entre el numero de ventas en los ultimos 7 dias / el numero de ventas entre lo vendido entre 7 y 14 dias antes.
		   - beneficioProducto;
		   - estadoBeneficioProducto;  // número (ej: beneficio €)
		
		   -beneficioSemana;
		    - estadoBeneficioSemana;
        	 */
        List<Producto> productos = productoRepository.findAll();
        List<ElementoListadoProductoRespuesta> resultado = new ArrayList<>(); 
        	
        	for (Producto producto : productos) {
        		 //  1. Obtener líneas de pedido DE ESTE PRODUCTO
                List<LineaPedido> lineasPedido = lineaPedidoRepositorio.findByProductoAndCantidadDisponibleGreaterThan(producto, 0);
                
             //  Inicializar 
                BigDecimal totalCoste = BigDecimal.ZERO;
                double totalUnidades = 0;
                
                             
             //  Recorrer líneas de pedido
                for (LineaPedido lineaPedido: lineasPedido) {
                	
                	double cantidadDisponible = lineaPedido.getCantidadDisponible();
                	BigDecimal precioUnitario = lineaPedido.getPrecioUnitario();
                	
                	  //  Solo usar stock disponible

                    BigDecimal cantidad = BigDecimal.valueOf(cantidadDisponible);

                    BigDecimal costeLinea = precioUnitario.multiply(cantidad);

                    totalCoste = totalCoste.add(costeLinea);

                    totalUnidades += cantidadDisponible;
                }

                BigDecimal precioCompraBD = BigDecimal.ZERO;

                if (totalUnidades > 0) {
                    precioCompraBD = totalCoste.divide(
                        BigDecimal.valueOf(totalUnidades),
                        2,
                        RoundingMode.HALF_UP
                    );
                }

                double precioCompra = precioCompraBD.doubleValue();
                
                
        		String nombre = producto.getNombre();
        		double precioVenta = producto.getPrecioVenta();
        		String tipo = producto.getTipo().name();
        		
        		   //  3. Crear DTO
                ElementoListadoProductoRespuesta productoRespuesta = new ElementoListadoProductoRespuesta();
                
                productoRespuesta.setNombre(nombre);
                productoRespuesta.setPrecioVenta(precioVenta);
                productoRespuesta.setTipo(tipo);
                productoRespuesta.setPrecioCompra(precioCompra);
               
                
                resultado.add(productoRespuesta);
		}
        	return resultado;
    }

    @Override
    public Producto obtenerPorId(int id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }

    @Override
    public Producto guardar(Producto producto) {

        //  VALIDACIONES PRO
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