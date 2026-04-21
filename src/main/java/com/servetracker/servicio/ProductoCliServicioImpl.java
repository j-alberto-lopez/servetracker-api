package com.servetracker.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servetracker.dtos.ElementoListadoProductoRespuesta;
import com.servetracker.modelo.Producto;
import com.servetracker.repositorio.ProductoRepositorio;

@Service
public class ProductoCliServicioImpl implements ProductoCliServicio {

    @Autowired
    private ProductoRepositorio productoRepository;

    @Override
    public List<ElementoListadoProductoRespuesta> obtenerProductosParaVista() {

        List<Producto> productos = productoRepository.findAll();

        return productos.stream().map(p -> {

            // 🔥 Aquí haces la lógica
            double precioCompra = 10; // ⚠️ aquí deberías sacarlo real si lo tienes
            double beneficio = p.getPrecioVenta() - precioCompra;

            double estadoVentas = p.getStockActual(); // ejemplo simple

            return new ElementoListadoProductoRespuesta(
                    p.getNombre(),
                    precioCompra,
                    p.getPrecioVenta(),
                    p.getTipo().name(),
                    estadoVentas,
                    beneficio,
                    "Proveedor X" // ⚠️ si no lo tienes aún
            );

        }).toList();
    }
}