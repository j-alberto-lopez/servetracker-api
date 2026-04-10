package com.servetracker.modelo;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "proveedor")
public class Proveedor {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String nombre;
	
	private String email;
	
	private String tlf;

	public Proveedor(int id, String nombre, String email, String tlf) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.tlf = tlf;
	}
	
	public Proveedor() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTlf() {
		return tlf;
	}

	public void setTlf(String tlf) {
		this.tlf = tlf;
	}

	@Override
	public String toString() {
		return "Proveedor [id=" + id + ", nombre=" + nombre + ", email=" + email + ", tlf=" + tlf + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id, nombre, tlf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Proveedor other = (Proveedor) obj;
		return Objects.equals(email, other.email) && id == other.id && Objects.equals(nombre, other.nombre)
				&& Objects.equals(tlf, other.tlf);
	}
	
	
}
