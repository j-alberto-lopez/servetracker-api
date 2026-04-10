package com.servetracker.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.servetracker.modelo.ConsumoStock;

@Repository
public interface ConsumoStockRepositorio extends JpaRepository<ConsumoStock, Integer>{

}