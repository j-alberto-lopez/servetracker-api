package com.servetracker.modelo;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "receta")
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "producto_id")
    private Producto productoFinal;

	public Receta(int id, Producto productoFinal) {
		super();
		this.id = id;
		this.productoFinal = productoFinal;
	}
	public Receta() {
		super();
	  }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Producto getProductoFinal() {
		return productoFinal;
	}
	public void setProductoFinal(Producto productoFinal) {
		this.productoFinal = productoFinal;
	}
	@Override
	public String toString() {
		return "Receta [id=" + id + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, productoFinal);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Receta other = (Receta) obj;
		return id == other.id && Objects.equals(productoFinal, other.productoFinal);
	}
    
    
}