package com.servetracker.servicio;
import com.servetracker.modelo.Producto;

public interface StockServicio {

    void descontarStock(Producto producto, int cantidad);

    void aumentarStock(Producto producto, int cantidad);

    boolean hayStockSuficiente(Producto producto, int cantidad);
    
    double calcularCosteFIFO(Producto producto);
    
    void descontarStockFIFO(Producto producto, int cantidad);

}