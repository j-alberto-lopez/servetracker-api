package com.servetracker.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servetracker.modelo.LineaPedido;
import com.servetracker.modelo.Producto;
import com.servetracker.repositorio.LineaPedidoRepositorio;

@Service
public class StockServicioImpl implements StockServicio {

    @Autowired
    private ProductoServicio productoServicio;
    @Autowired
    private LineaPedidoRepositorio lineaPedidoRepositorio;

    @Override
    public void descontarStock(Producto producto, int cantidad) {

        if (!hayStockSuficiente(producto, cantidad)) {
            throw new RuntimeException("Stock insuficiente");
        }

        producto.setStockActual(producto.getStockActual() - cantidad);

        productoServicio.guardar(producto);
    }
    @Override
    public void descontarStockFIFO(Producto producto, int cantidad) {

        if (!hayStockSuficiente(producto, cantidad)) {
            throw new RuntimeException("Stock insuficiente");
        }

        List<LineaPedido> lineas = lineaPedidoRepositorio
            .findByProductoAndCantidadDisponibleGreaterThanOrderByPedidoFechaAsc(producto, 0);

        int restante = cantidad;

        for (LineaPedido lp : lineas) {

            if (restante <= 0) break;

            int disponible = lp.getCantidadDisponible();

            if (disponible <= 0) continue;

            if (disponible >= restante) {
                lp.setCantidadDisponible(disponible - restante);
                restante = 0;
            } else {
                lp.setCantidadDisponible(0);
                restante -= disponible;
            }

            lineaPedidoRepositorio.save(lp);
        }

        producto.setStockActual(producto.getStockActual() - cantidad);
        productoServicio.guardar(producto);
    }
    @Override
    public void aumentarStock(Producto producto, int cantidad) {

        producto.setStockActual(producto.getStockActual() + cantidad);

        productoServicio.guardar(producto);
    }

    @Override
    public boolean hayStockSuficiente(Producto producto, int cantidad) {
        return producto.getStockActual() >= cantidad;
    }
    @Override
    public double calcularCosteFIFO(Producto producto) {

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

        return costeTotal / producto.getStockActual();
    }
}