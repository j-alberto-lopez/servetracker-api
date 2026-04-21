package com.servetracker.dtos;

import java.util.Objects;

public class ElementoListadoProductoRespuesta {

	private String nombre;
	private double precioCompra;
	private double precioVenta;
	private String tipo;

	private int ventasSemanales;
	private double estadoVentas; // número (ej: cantidad vendida)

	private double beneficioProducto;
	private double estadoBeneficioProducto; // número (ej: beneficio €)

	private double beneficioSemana;
	private double estadoBeneficioSemana; // número (ej: beneficio €)

	private String proveedor;

	// 🔹 Constructor vacío
	public ElementoListadoProductoRespuesta() {
	}

	// 🔹 Constructor completo
	public ElementoListadoProductoRespuesta(String nombre, double precioCompra, double precioVenta, String tipo,
			double estadoVentas, double estadoBeneficioProducto, String proveedor) {
		this.nombre = nombre;
		this.precioCompra = precioCompra;
		this.precioVenta = precioVenta;
		this.tipo = tipo;
		this.estadoVentas = estadoVentas;
		this.estadoBeneficioProducto = estadoBeneficioProducto;
		this.proveedor = proveedor;
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

	public double getEstadoVentas() {
		return estadoVentas;
	}

	public void setEstadoVentas(double estadoVentas) {
		this.estadoVentas = estadoVentas;
	}

	public double getEstadoBeneficioProducto() {
		return estadoBeneficioProducto;
	}

	public void setEstadoBeneficioProducto(double estadoBeneficioProducto) {
		this.estadoBeneficioProducto = estadoBeneficioProducto;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	// 🔹 toString

	@Override
	public String toString() {
		return "ProductoCli [nombre=" + nombre + ", precioCompra=" + precioCompra + ", precioVenta=" + precioVenta
				+ ", tipo=" + tipo + ", estadoVentas=" + estadoVentas + ", estadoBeneficio=" + estadoBeneficioProducto
				+ ", proveedor=" + proveedor + "]";
	}

	// 🔹 equals & hashCode

	@Override
	public int hashCode() {
		return Objects.hash(nombre, precioCompra, precioVenta, tipo, estadoVentas, estadoBeneficioProducto, proveedor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementoListadoProductoRespuesta))
			return false;
		ElementoListadoProductoRespuesta other = (ElementoListadoProductoRespuesta) obj;
		return Objects.equals(nombre, other.nombre)
				&& Double.doubleToLongBits(precioCompra) == Double.doubleToLongBits(other.precioCompra)
				&& Double.doubleToLongBits(precioVenta) == Double.doubleToLongBits(other.precioVenta)
				&& Objects.equals(tipo, other.tipo)
				&& Double.doubleToLongBits(estadoVentas) == Double.doubleToLongBits(other.estadoVentas)
				&& Double.doubleToLongBits(estadoBeneficioProducto) == Double
						.doubleToLongBits(other.estadoBeneficioProducto)
				&& Objects.equals(proveedor, other.proveedor);
	}
}
