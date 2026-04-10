package com.servetracker.modelo;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "alerta")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private TipoAlerta tipo;

    private String mensaje;

    private boolean activa;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

	public Alerta(int id, TipoAlerta tipo, String mensaje, boolean activa, Producto producto) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.mensaje = mensaje;
		this.activa = activa;
		this.producto = producto;
	}
    
	public Alerta() {
		super();
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TipoAlerta getTipo() {
		return tipo;
	}

	public void setTipo(TipoAlerta tipo) {
		this.tipo = tipo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	@Override
	public String toString() {
		return "Alerta [id=" + id + ", tipo=" + tipo + ", mensaje=" + mensaje + ", activa=" + activa + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(activa, id, mensaje, producto, tipo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alerta other = (Alerta) obj;
		return activa == other.activa && id == other.id && Objects.equals(mensaje, other.mensaje)
				&& Objects.equals(producto, other.producto) && tipo == other.tipo;
	}
    
}
