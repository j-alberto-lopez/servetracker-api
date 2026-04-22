package com.servetracker.repositorio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servetracker.modelo.LineaTicket;
import com.servetracker.modelo.Producto;

@Repository
public interface LineaTicketRepositorio extends JpaRepository<LineaTicket, Integer> {

	List<LineaTicket> findByProductoAndTicketFechaGreaterThanEqualAndTicketFechaLessThan(
		    Producto producto,
		    LocalDate inicio,
		    LocalDate fin
		);
	
}
