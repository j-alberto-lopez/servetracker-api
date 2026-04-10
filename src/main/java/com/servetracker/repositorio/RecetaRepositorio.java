package com.servetracker.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servetracker.modelo.Receta;

@Repository
public interface RecetaRepositorio extends JpaRepository<Receta, Integer>{

}
