package com.servetracker.servicio;

import java.util.List;

import com.servetracker.dtos.ElementoListadoProductoRespuesta;
import com.servetracker.modelo.Producto;

public interface ProductoServicio {

    List<ElementoListadoProductoRespuesta> obtenerListado();

    Producto obtenerPorId(int id);

    Producto guardar(Producto producto);

    void eliminar(int id);

    List<Producto> obtenerConStockActual();

}
