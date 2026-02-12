package com.library.scheduled;

import com.library.entity.Prestamo;
import com.library.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OverdueLoansTask {

    @Autowired
    private PrestamoService prestamoService;

    @Scheduled(cron = "0 0 0 * * ?") // Run every day at midnight
    public void checkOverdueLoans() {
        List<Prestamo> overdueLoans = prestamoService.getOverdueLoans();

        for (Prestamo prestamo : overdueLoans) {
            System.out.println("ALERTA: El préstamo del libro '" + prestamo.getLibro().getTitulo() +
                    "' al usuario '" + prestamo.getUsuario().getUsername() + "' está vencido.");
        }
    }
}
