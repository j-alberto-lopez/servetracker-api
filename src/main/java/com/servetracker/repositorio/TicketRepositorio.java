package com.servetracker.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servetracker.modelo.Ticket;

@Repository
public interface TicketRepositorio extends JpaRepository<Ticket, Integer> {
	
}
