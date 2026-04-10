package com.servetracker.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servetracker.modelo.IngredienteReceta;

@Repository
public interface IngredienteRecetaRepositorio extends JpaRepository<IngredienteReceta, Integer>{

}
