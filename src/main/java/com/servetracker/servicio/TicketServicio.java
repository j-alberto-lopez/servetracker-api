package com.servetracker.servicio;

import com.servetracker.modelo.Ticket;

public interface TicketServicio {

    Ticket crearTicket();

    Ticket obtenerPorId(int id);

    void agregarProducto(int ticketId, int productoId, int cantidad);

    double calcularTotal(int ticketId);

}