package com.servetracker.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servetracker.dtos.ElementoListadoProductoRespuesta;

@Service
public class ProductoCliServicioImpl implements ProductoCliServicio {

    @Autowired
    private ProductoServicio productoServicio; 

    @Override
    public List<ElementoListadoProductoRespuesta> obtenerProductosParaVista() {

        return productoServicio.obtenerListado();
    }
}