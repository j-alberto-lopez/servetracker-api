package com.servetracker.modelo;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private LocalDate fecha;
	
	@ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

	public Pedido(int id, LocalDate fecha, Proveedor proveedor) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.proveedor = proveedor;
	}
	public Pedido() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public Proveedor getProveedor() {
		return proveedor;
	}
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	@Override
	public String toString() {
		return "Pedido [id=" + id + ", fecha=" + fecha + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(fecha, id, proveedor);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return Objects.equals(fecha, other.fecha) && id == other.id && Objects.equals(proveedor, other.proveedor);
	}
	
}
