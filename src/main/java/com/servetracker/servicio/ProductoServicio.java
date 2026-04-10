package com.servetracker.servicio;

import java.util.List;

import com.servetracker.modelo.Producto;

public interface ProductoServicio {

    List<Producto> obtenerTodos();

    Producto obtenerPorId(int id);

    Producto guardar(Producto producto);

    void eliminar(int id);

    List<Producto> obtenerConStockActual();

}
