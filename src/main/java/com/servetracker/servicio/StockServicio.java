package com.servetracker.servicio;
import com.servetracker.modelo.Producto;

public interface StockServicio {

    void descontarStock(Producto producto, double cantidad);

    void aumentarStock(Producto producto, double cantidad);

    boolean hayStockSuficiente(Producto producto, double cantidad);

}