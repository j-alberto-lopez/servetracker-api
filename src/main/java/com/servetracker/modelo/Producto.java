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
@Table(name= "producto")
public class Producto {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nombre;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Enumerated(EnumType.STRING)
	private TipoProducto tipo;
	
	private double precioVenta;
	
	private double stockActual;
	
	private double stockMin;
	
	private String unidadMedida;
	
	   @ManyToOne
	   @JoinColumn(name = "tipo_iva_id")
	   private TipoIVA tipoIVA;

	   public Producto(int id,String nombre, TipoProducto tipo, double precioVenta, double stockActual, double stockMin,
			String unidadMedida, TipoIVA tipoIVA) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.precioVenta = precioVenta;
		this.stockActual = stockActual;
		this.stockMin = stockMin;
		this.unidadMedida = unidadMedida;
		this.tipoIVA = tipoIVA;
	   }
	   
	   public Producto() {
			super();
		   }

	   public int getId() {
		   return id;
	   }

	   public void setId(int id) {
		   this.id = id;
	   }

	   public TipoProducto getTipo() {
		   return tipo;
	   }

	   public void setTipo(TipoProducto tipo) {
		   this.tipo = tipo;
	   }

	   public double getPrecioVenta() {
		   return precioVenta;
	   }

	   public void setPrecioVenta(double precioVenta) {
		   this.precioVenta = precioVenta;
	   }

	   public double getStockActual() {
		   return stockActual;
	   }

	   public void setStockActual(double stockActual) {
		   this.stockActual = stockActual;
	   }

	   public double getStockMin() {
		   return stockMin;
	   }

	   public void setStockMin(double stockMin) {
		   this.stockMin = stockMin;
	   }

	   public String getUnidadMedida() {
		   return unidadMedida;
	   }

	   public void setUnidadMedida(String unidadMedida) {
		   this.unidadMedida = unidadMedida;
	   }

	   public TipoIVA getTipoIVA() {
		   return tipoIVA;
	   }

	   public void setTipoIVA(TipoIVA tipoIVA) {
		   this.tipoIVA = tipoIVA;
	   }

	   @Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", tipo=" + tipo + ", precioVenta=" + precioVenta
				+ ", stockActual=" + stockActual + ", stockMin=" + stockMin + ", unidadMedida=" + unidadMedida
				+ ", tipoIVA=" + tipoIVA + "]";
	}

	   @Override
	public int hashCode() {
		return Objects.hash(id, nombre, precioVenta, stockActual, stockMin, tipo, tipoIVA, unidadMedida);
	}

	   @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return id == other.id && Objects.equals(nombre, other.nombre)
				&& Double.doubleToLongBits(precioVenta) == Double.doubleToLongBits(other.precioVenta)
				&& Double.doubleToLongBits(stockActual) == Double.doubleToLongBits(other.stockActual)
				&& Double.doubleToLongBits(stockMin) == Double.doubleToLongBits(other.stockMin) && tipo == other.tipo
				&& Objects.equals(tipoIVA, other.tipoIVA) && Objects.equals(unidadMedida, other.unidadMedida);
	}
	
	
}
