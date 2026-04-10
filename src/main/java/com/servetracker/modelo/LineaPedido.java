package com.servetracker.modelo;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "linea_pedido")
public class LineaPedido {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private double cantidad;
	
	private double precioTotal;
	
	private double precioUnitario;
	
	private double cantidadDisponible;
	
	 @ManyToOne
	 @JoinColumn(name = "producto_id")
	 private Producto producto;

	 public LineaPedido(int id, double cantidad, double precioTotal, double precioUnitario, double cantidadDisponible,
			Producto producto) {
		super();
		this.id = id;
		this.cantidad = cantidad;
		this.precioTotal = precioTotal;
		this.precioUnitario = precioUnitario;
		this.cantidadDisponible = cantidadDisponible;
		this.producto = producto;
	 }
	 
	 public LineaPedido() {
			super();
			 }

	 public int getId() {
		 return id;
	 }

	 public void setId(int id) {
		 this.id = id;
	 }

	 public double getCantidad() {
		 return cantidad;
	 }

	 public void setCantidad(double cantidad) {
		 this.cantidad = cantidad;
	 }

	 public double getPrecioTotal() {
		 return precioTotal;
	 }

	 public void setPrecioTotal(double precioTotal) {
		 this.precioTotal = precioTotal;
	 }

	 public double getPrecioUnitario() {
		 return precioUnitario;
	 }

	 public void setPrecioUnitario(double precioUnitario) {
		 this.precioUnitario = precioUnitario;
	 }

	 public double getCantidadDisponible() {
		 return cantidadDisponible;
	 }

	 public void setCantidadDisponible(double cantidadDisponible) {
		 this.cantidadDisponible = cantidadDisponible;
	 }

	 public Producto getProducto() {
		 return producto;
	 }

	 public void setProducto(Producto producto) {
		 this.producto = producto;
	 }

	 @Override
	 public String toString() {
		return "LineaPedido [id=" + id + ", cantidad=" + cantidad + ", precioTotal=" + precioTotal + ", precioUnitario="
				+ precioUnitario + ", cantidadDisponible=" + cantidadDisponible + ", producto=" + producto + "]";
	 }

	 @Override
	 public int hashCode() {
		return Objects.hash(cantidad, cantidadDisponible, id, precioTotal, precioUnitario, producto);
	 }

	 @Override
	 public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LineaPedido other = (LineaPedido) obj;
		return Double.doubleToLongBits(cantidad) == Double.doubleToLongBits(other.cantidad)
				&& Double.doubleToLongBits(cantidadDisponible) == Double.doubleToLongBits(other.cantidadDisponible)
				&& id == other.id && Double.doubleToLongBits(precioTotal) == Double.doubleToLongBits(other.precioTotal)
				&& Double.doubleToLongBits(precioUnitario) == Double.doubleToLongBits(other.precioUnitario)
				&& Objects.equals(producto, other.producto);
	 }
	
	 
}
