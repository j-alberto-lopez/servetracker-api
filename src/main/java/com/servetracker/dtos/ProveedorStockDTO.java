package com.servetracker.dtos;

import java.util.Objects;

public class ProveedorStockDTO {

    private String nombre;
    private double cantidad;

    // 🔹 Constructor vacío
    public ProveedorStockDTO() {
    }

    // 🔹 Constructor completo
    public ProveedorStockDTO(String nombre, double cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    // 🔹 Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    // 🔹 toString

    @Override
    public String toString() {
        return "ProveedorStockDTO [nombre=" + nombre + ", cantidad=" + cantidad + "]";
    }

    // 🔹 equals & hashCode

    @Override
    public int hashCode() {
        return Objects.hash(nombre, cantidad);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof ProveedorStockDTO))
            return false;
        ProveedorStockDTO other = (ProveedorStockDTO) obj;
        return Objects.equals(nombre, other.nombre)
                && Double.doubleToLongBits(cantidad) == Double.doubleToLongBits(other.cantidad);
    }
}