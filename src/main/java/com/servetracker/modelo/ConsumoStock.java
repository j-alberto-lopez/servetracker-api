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
@Table(name = "consumo_stock")
public class ConsumoStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double cantidadConsumida;

    private double costeUnitario;

    @ManyToOne
    @JoinColumn(name = "linea_ticket_id")
    private LineaTicket lineaTicket;

    @ManyToOne
    @JoinColumn(name = "linea_pedido_id")
    private LineaPedido lineaPedido;

	public ConsumoStock(int id, double cantidadConsumida, double costeUnitario, LineaTicket lineaTicket,
			LineaPedido lineaPedido) {
		super();
		this.id = id;
		this.cantidadConsumida = cantidadConsumida;
		this.costeUnitario = costeUnitario;
		this.lineaTicket = lineaTicket;
		this.lineaPedido = lineaPedido;
	}
	public ConsumoStock() {
		super();
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getCantidadConsumida() {
		return cantidadConsumida;
	}
	public void setCantidadConsumida(double cantidadConsumida) {
		this.cantidadConsumida = cantidadConsumida;
	}
	public double getCosteUnitario() {
		return costeUnitario;
	}
	public void setCosteUnitario(double costeUnitario) {
		this.costeUnitario = costeUnitario;
	}
	public LineaTicket getLineaTicket() {
		return lineaTicket;
	}
	public void setLineaTicket(LineaTicket lineaTicket) {
		this.lineaTicket = lineaTicket;
	}
	public LineaPedido getLineaPedido() {
		return lineaPedido;
	}
	public void setLineaPedido(LineaPedido lineaPedido) {
		this.lineaPedido = lineaPedido;
	}
	@Override
	public String toString() {
		return "ConsumoStock [id=" + id + ", cantidadConsumida=" + cantidadConsumida + ", costeUnitario="
				+ costeUnitario + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(cantidadConsumida, costeUnitario, id, lineaPedido, lineaTicket);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConsumoStock other = (ConsumoStock) obj;
		return Double.doubleToLongBits(cantidadConsumida) == Double.doubleToLongBits(other.cantidadConsumida)
				&& Double.doubleToLongBits(costeUnitario) == Double.doubleToLongBits(other.costeUnitario)
				&& id == other.id && Objects.equals(lineaPedido, other.lineaPedido)
				&& Objects.equals(lineaTicket, other.lineaTicket);
	}

     
 }
