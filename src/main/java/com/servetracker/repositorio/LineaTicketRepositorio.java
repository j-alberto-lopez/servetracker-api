package com.servetracker.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servetracker.modelo.LineaTicket;

@Repository
public interface LineaTicketRepositorio extends JpaRepository<LineaTicket, Integer> {

}
