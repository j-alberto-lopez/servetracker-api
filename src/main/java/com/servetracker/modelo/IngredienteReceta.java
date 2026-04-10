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
@Table(name = "ingrediente_receta")
public class IngredienteReceta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private double cantidad;

	@ManyToOne
	@JoinColumn(name = "receta_id")
	private Receta receta;

	@ManyToOne
	@JoinColumn(name = "producto_id")
	private Producto productoIngrediente;

	public IngredienteReceta(int id, double cantidad, Receta receta, Producto productoIngrediente) {
		super();
		this.id = id;
		this.cantidad = cantidad;
		this.receta = receta;
		this.productoIngrediente = productoIngrediente;
	}

	public IngredienteReceta() {
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

	public Receta getReceta() {
		return receta;
	}

	public void setReceta(Receta receta) {
		this.receta = receta;
	}

	public Producto getProductoIngrediente() {
		return productoIngrediente;
	}

	public void setProductoIngrediente(Producto productoIngrediente) {
		this.productoIngrediente = productoIngrediente;
	}

	@Override
	public String toString() {
		return "IngredienteReceta [id=" + id + ", cantidad=" + cantidad + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cantidad, id, productoIngrediente, receta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IngredienteReceta other = (IngredienteReceta) obj;
		return Double.doubleToLongBits(cantidad) == Double.doubleToLongBits(other.cantidad) && id == other.id
				&& Objects.equals(productoIngrediente, other.productoIngrediente)
				&& Objects.equals(receta, other.receta);
	}

}
