package com.servetracker.servicio;

import java.util.List;

import com.servetracker.dtos.ElementoListadoProductoRespuesta;

public interface ProductoCliServicio {

    List<ElementoListadoProductoRespuesta> obtenerProductosParaVista();

}