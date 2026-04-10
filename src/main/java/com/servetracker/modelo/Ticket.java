package com.servetracker.modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate fecha;

    @OneToMany(mappedBy = "ticket")
    private List<LineaTicket> lineas;

	public Ticket(int id, LocalDate fecha, List<LineaTicket> lineas) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.lineas = lineas;
	}

	public Ticket() {
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

	public List<LineaTicket> getLineas() {
		return lineas;
	}

	public void setLineas(List<LineaTicket> lineas) {
		this.lineas = lineas;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", fecha=" + fecha + ", lineas=" + lineas + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(fecha, id, lineas);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		return Objects.equals(fecha, other.fecha) && id == other.id && Objects.equals(lineas, other.lineas);
	}
	
	
   

}