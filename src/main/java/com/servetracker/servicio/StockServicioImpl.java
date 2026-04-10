package com.servetracker.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servetracker.modelo.Producto;

@Service
public class StockServicioImpl implements StockServicio {

    @Autowired
    private ProductoServicio productoServicio;

    @Override
    public void descontarStock(Producto producto, double cantidad) {

        if (!hayStockSuficiente(producto, cantidad)) {
            throw new RuntimeException("Stock insuficiente");
        }

        producto.setStockActual(producto.getStockActual() - cantidad);

        productoServicio.guardar(producto);
    }

    @Override
    public void aumentarStock(Producto producto, double cantidad) {

        producto.setStockActual(producto.getStockActual() + cantidad);

        productoServicio.guardar(producto);
    }

    @Override
    public boolean hayStockSuficiente(Producto producto, double cantidad) {
        return producto.getStockActual() >= cantidad;
    }
}