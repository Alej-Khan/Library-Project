package com.library.repository;

import com.library.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByUsuarioUsername(String username);
    
    @Query("SELECT p FROM Prestamo p WHERE p.fechaDevolucion IS NULL AND p.fechaPrestamo < :cutoffDate")
    List<Prestamo> findOverdueLoans(LocalDate cutoffDate);
}
