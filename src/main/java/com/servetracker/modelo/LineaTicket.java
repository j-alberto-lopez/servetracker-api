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

    private int cantidad;

    private double precioVentaUnitario;
    
    private double costeUnitarioEnVenta;
    
    private double beneficioUnitarioEnVenta;

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
	public LineaTicket(int id, int cantidad, double precioVentaUnitario, double costeUnitarioEnVenta,
			double beneficioUnitarioEnVenta, double ivaAplicado, Producto producto, Ticket ticket) {
		super();
		this.id = id;
		this.cantidad = cantidad;
		this.precioVentaUnitario = precioVentaUnitario;
		this.costeUnitarioEnVenta = costeUnitarioEnVenta;
		this.beneficioUnitarioEnVenta = beneficioUnitarioEnVenta;
		this.ivaAplicado = ivaAplicado;
		this.producto = producto;
		this.ticket = ticket;
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
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public double getPrecioVentaUnitario() {
		return precioVentaUnitario;
	}
	public void setPrecioVentaUnitario(double precioVentaUnitario) {
		this.precioVentaUnitario = precioVentaUnitario;
	}
	public double getCosteUnitarioEnVenta() {
		return costeUnitarioEnVenta;
	}
	public void setCosteUnitarioEnVenta(double costeUnitarioEnVenta) {
		this.costeUnitarioEnVenta = costeUnitarioEnVenta;
	}
	public double getBeneficioUnitarioEnVenta() {
		return beneficioUnitarioEnVenta;
	}
	public void setBeneficioUnitarioEnVenta(double beneficioUnitarioEnVenta) {
		this.beneficioUnitarioEnVenta = beneficioUnitarioEnVenta;
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
		return "LineaTicket [id=" + id + ", cantidad=" + cantidad + ", precioVentaUnitario=" + precioVentaUnitario
				+ ", costeUnitarioEnVenta=" + costeUnitarioEnVenta + ", beneficioUnitarioEnVenta="
				+ beneficioUnitarioEnVenta + ", ivaAplicado=" + ivaAplicado + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(beneficioUnitarioEnVenta, cantidad, costeUnitarioEnVenta, id, ivaAplicado,
				precioVentaUnitario, producto, ticket);
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
		return Double.doubleToLongBits(beneficioUnitarioEnVenta) == Double
				.doubleToLongBits(other.beneficioUnitarioEnVenta) && cantidad == other.cantidad
				&& Double.doubleToLongBits(costeUnitarioEnVenta) == Double.doubleToLongBits(other.costeUnitarioEnVenta)
				&& id == other.id && Double.doubleToLongBits(ivaAplicado) == Double.doubleToLongBits(other.ivaAplicado)
				&& Double.doubleToLongBits(precioVentaUnitario) == Double.doubleToLongBits(other.precioVentaUnitario)
				&& Objects.equals(producto, other.producto) && Objects.equals(ticket, other.ticket);
	}
	
	
}
