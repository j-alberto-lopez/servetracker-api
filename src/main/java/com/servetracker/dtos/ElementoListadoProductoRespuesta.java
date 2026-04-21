package com.servetracker.dtos;

import java.util.ArrayList;
import java.util.List;

public class ElementoListadoProductoRespuesta {

    private String nombre;
    private double precioCompra;
    private double precioVenta;
    private String tipo;

    private int ventasSemanales;
    private double estadoVentas;

    private double beneficioProducto;
    private double estadoBeneficioProducto;

    private double beneficioSemana;
    private double estadoBeneficioSemana;

    private List<ProveedorStockDTO> proveedores;

    // 🔹 Constructor vacío 
    public ElementoListadoProductoRespuesta() {
        this.proveedores = new ArrayList<>(); 
    }

    // 🔹 Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getVentasSemanales() {
        return ventasSemanales;
    }

    public void setVentasSemanales(int ventasSemanales) {
        this.ventasSemanales = ventasSemanales;
    }

    public double getEstadoVentas() {
        return estadoVentas;
    }

    public void setEstadoVentas(double estadoVentas) {
        this.estadoVentas = estadoVentas;
    }

    public double getBeneficioProducto() {
        return beneficioProducto;
    }

    public void setBeneficioProducto(double beneficioProducto) {
        this.beneficioProducto = beneficioProducto;
    }

    public double getEstadoBeneficioProducto() {
        return estadoBeneficioProducto;
    }

    public void setEstadoBeneficioProducto(double estadoBeneficioProducto) {
        this.estadoBeneficioProducto = estadoBeneficioProducto;
    }

    public double getBeneficioSemana() {
        return beneficioSemana;
    }

    public void setBeneficioSemana(double beneficioSemana) {
        this.beneficioSemana = beneficioSemana;
    }

    public double getEstadoBeneficioSemana() {
        return estadoBeneficioSemana;
    }

    public void setEstadoBeneficioSemana(double estadoBeneficioSemana) {
        this.estadoBeneficioSemana = estadoBeneficioSemana;
    }

    public List<ProveedorStockDTO> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<ProveedorStockDTO> proveedores) {
        this.proveedores = proveedores;
    }

    @Override
    public String toString() {
        return "ElementoListadoProductoRespuesta{" +
                "nombre='" + nombre + '\'' +
                ", precioCompra=" + precioCompra +
                ", precioVenta=" + precioVenta +
                ", tipo='" + tipo + '\'' +
                ", proveedores=" + proveedores +
                '}';
    }
}

	