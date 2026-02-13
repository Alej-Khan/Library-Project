package com.library.scheduled;

import com.library.entity.Prestamo;
import com.library.service.PrestamoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OverdueLoansTask {

    private static final Logger logger = LoggerFactory.getLogger(OverdueLoansTask.class);

    @Autowired
    private PrestamoService prestamoService;

    @Scheduled(cron = "0 0 0 * * ?") // Run every day at midnight
    public void checkOverdueLoans() {
        List<Prestamo> overdueLoans = prestamoService.getOverdueLoans();

        for (Prestamo prestamo : overdueLoans) {
            logger.warn("ALERTA: El préstamo del libro '{}' al usuario '{}' está vencido.",
                    prestamo.getLibro().getTitulo(),
                    prestamo.getUsuario().getUsername());
        }
    }
}
