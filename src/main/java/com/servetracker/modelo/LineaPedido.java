package com.servetracker.modelo;

import java.math.BigDecimal;

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
	private Long id;
	
	private int cantidad;
	
	public int getCantidad() {
		return cantidad;
	}
	private BigDecimal precioTotal;
	
	private BigDecimal precioUnitario;
	
	private int cantidadDisponible;
	
	@ManyToOne
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;
	 public Pedido getPedido() {
		return pedido;
	}
	 @ManyToOne
	 @JoinColumn(name = "producto_id")
	 private Producto producto;

	 public LineaPedido(int cantidad, BigDecimal precioTotal, BigDecimal precioUnitario, int cantidadDisponible,
			Producto producto) {
		super();
		this.id = null;
		this.cantidad = cantidad;
		this.precioTotal = precioTotal;
		this.precioUnitario = precioUnitario;
		this.cantidadDisponible = cantidadDisponible;
		this.producto = producto;
	 }
	 public LineaPedido() {
			super();
		 }
	 @Override
	 public String toString() {
		return "LineaPedido [id=" + id + ", cantidad=" + cantidad + ", precioTotal=" + precioTotal + ", precioUnitario="
				+ precioUnitario + ", cantidadDisponible=" + cantidadDisponible + "]";
	 }
	 public BigDecimal getPrecioUnitario() {
		 return precioUnitario;
	 }
	 public void setPrecioUnitario(BigDecimal precioUnitario) {
		 this.precioUnitario = precioUnitario;
	 }
	 public int getCantidadDisponible() {
		 return cantidadDisponible;
	 }
	 public void setCantidadDisponible(int cantidadDisponible) {
		 this.cantidadDisponible = cantidadDisponible;
	 }
	 

}