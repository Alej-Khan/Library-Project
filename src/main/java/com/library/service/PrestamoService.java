package com.library.service;

import com.library.entity.Libro;
import com.library.entity.Prestamo;
import com.library.entity.Usuario;
import com.library.repository.LibroRepository;
import com.library.repository.PrestamoRepository;
import com.library.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Prestamo prestarLibro(Long libroId, Long usuarioId) {
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        if (!libro.isDisponible()) {
            throw new RuntimeException("El libro no está disponible para préstamo");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        libro.setDisponible(false);
        libroRepository.save(libro);

        Prestamo prestamo = new Prestamo();
        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);
        prestamo.setFechaPrestamo(LocalDate.now());

        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo devolverLibro(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        if (prestamo.getFechaDevolucion() != null) {
            throw new RuntimeException("El libro ya fue devuelto");
        }

        prestamo.setFechaDevolucion(LocalDate.now());
        Libro libro = prestamo.getLibro();
        libro.setDisponible(true);
        libroRepository.save(libro);

        return prestamoRepository.save(prestamo);
    }

    public List<Prestamo> getMisPrestamos(String username) {
        return prestamoRepository.findByUsuarioUsername(username);
    }

    public List<Prestamo> getOverdueLoans() {
        LocalDate cutoffDate = LocalDate.now().minusDays(14);
        return prestamoRepository.findOverdueLoans(cutoffDate);
    }
}
