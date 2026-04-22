package com.servetracker.servicio;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servetracker.modelo.LineaTicket;
import com.servetracker.modelo.Producto;
import com.servetracker.modelo.Ticket;
import com.servetracker.repositorio.LineaTicketRepositorio;
import com.servetracker.repositorio.TicketRepositorio;

import jakarta.transaction.Transactional;

@Service
public class TicketServicioImpl implements TicketServicio {

    @Autowired
    private StockServicio stockServicio;

    @Autowired
    private TicketRepositorio ticketRepository;

    @Autowired
    private ProductoServicio productoService;

    @Autowired
    private LineaTicketRepositorio lineaTicketRepository;

    @Override
    public Ticket crearTicket() {
        Ticket t = new Ticket();
        t.setFecha(LocalDate.now());
        return ticketRepository.save(t);
    }

    @Override
    public Ticket obtenerPorId(int id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
    }
    @Transactional
    @Override
    public void agregarProducto(int ticketId, int productoId, int cantidad) {

        Ticket ticket = obtenerPorId(ticketId);
        Producto producto = productoService.obtenerPorId(productoId);
        
     // 🔹 2. Validar stock
        if (!stockServicio.hayStockSuficiente(producto, cantidad)) {
            throw new RuntimeException("Stock insuficiente");
        }
        
        double costeUnitario = stockServicio.calcularCosteFIFO(producto);
        double beneficioUnitario = producto.getPrecioVenta() - costeUnitario;

        // 🔥 crear línea
        LineaTicket linea = new LineaTicket();
        linea.setTicket(ticket);
        linea.setProducto(producto);
        linea.setCantidad(cantidad);
        linea.setPrecioVentaUnitario(producto.getPrecioVenta());
        linea.setCosteUnitarioEnVenta(costeUnitario);
        linea.setBeneficioUnitarioEnVenta(beneficioUnitario);

        // 🔥 IVA seguro
        if (producto.getTipoIVA() != null) {
            linea.setIvaAplicado(producto.getTipoIVA().getPorcentaje());
        } else {
            linea.setIvaAplicado(0);
        }

        lineaTicketRepository.save(linea);

        // 🔥 descontar stock (centralizado)
     // 🔹 8. Descontar stock FIFO (antes descontaba stock pero no unidades disponibles del pedido)
        stockServicio.descontarStockFIFO(producto, cantidad);
    }

    @Override
    public double calcularTotal(int ticketId) {

        Ticket ticket = obtenerPorId(ticketId);

        if (ticket.getLineas() == null) return 0;

        double total = 0;

        for (LineaTicket l : ticket.getLineas()) {

            double subtotal = l.getPrecioVentaUnitario() * l.getCantidad();
            double iva = subtotal * (l.getIvaAplicado() / 100);

            total += subtotal + iva;
        }

        return total;
    }
}