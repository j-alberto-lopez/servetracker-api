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
@Table(name = "linea_ticket")
public class LineaTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double cantidad;

    private double precioVentaUnitario;

    private double ivaAplicado;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
    
    
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
    
    public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	

	public LineaTicket(int id, double cantidad, double precioVentaUnitario, double ivaAplicado, Producto producto) {
		super();
		this.id = id;
		this.cantidad = cantidad;
		this.precioVentaUnitario = precioVentaUnitario;
		this.ivaAplicado = ivaAplicado;
		this.producto = producto;
	}
	public LineaTicket() {
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
	public double getPrecioVentaUnitario() {
		return precioVentaUnitario;
	}
	public void setPrecioVentaUnitario(double precioVentaUnitario) {
		this.precioVentaUnitario = precioVentaUnitario;
	}
	public double getIvaAplicado() {
		return ivaAplicado;
	}
	public void setIvaAplicado(double ivaAplicado) {
		this.ivaAplicado = ivaAplicado;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	@Override
	public String toString() {
	    return "LineaTicket [id=" + id + ", cantidad=" + cantidad +
	            ", precioVentaUnitario=" + precioVentaUnitario +
	            ", ivaAplicado=" + ivaAplicado + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(cantidad, id, ivaAplicado, precioVentaUnitario, producto);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LineaTicket other = (LineaTicket) obj;
		return Double.doubleToLongBits(cantidad) == Double.doubleToLongBits(other.cantidad) && id == other.id
				&& Double.doubleToLongBits(ivaAplicado) == Double.doubleToLongBits(other.ivaAplicado)
				&& Double.doubleToLongBits(precioVentaUnitario) == Double.doubleToLongBits(other.precioVentaUnitario)
				&& Objects.equals(producto, other.producto);
	}
    
}
