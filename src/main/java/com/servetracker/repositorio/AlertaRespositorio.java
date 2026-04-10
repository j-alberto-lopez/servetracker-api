package com.servetracker.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servetracker.modelo.Alerta;

@Repository
public interface AlertaRespositorio extends JpaRepository<Alerta, Integer>{

}


