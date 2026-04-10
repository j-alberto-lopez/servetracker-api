package com.servetracker.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servetracker.modelo.LineaPedido;

@Repository
public interface LineaPedidoRepositorio extends JpaRepository<LineaPedido, Integer>{

}
