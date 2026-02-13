package com.library.controller;

import com.library.entity.Prestamo;
import com.library.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @PostMapping("/prestar/libro/{libroId}/usuario/{usuarioId}")
    @PreAuthorize("hasRole('BIBLIOTECARIO')")
    public ResponseEntity<Prestamo> prestarLibro(@PathVariable Long libroId, @PathVariable Long usuarioId) {
        Prestamo prestamo = prestamoService.prestarLibro(libroId, usuarioId);
        return ResponseEntity.ok(prestamo);
    }

    @PutMapping("/{prestamoId}/devolver")
    @PreAuthorize("hasRole('BIBLIOTECARIO')")
    public ResponseEntity<Prestamo> devolverLibro(@PathVariable Long prestamoId) {
        Prestamo prestamo = prestamoService.devolverLibro(prestamoId);
        return ResponseEntity.ok(prestamo);
    }

    @GetMapping("/mis-prestamos")
    @PreAuthorize("hasRole('MIEMBRO') or hasRole('BIBLIOTECARIO')")
    public ResponseEntity<List<Prestamo>> getMisPrestamos(Authentication authentication) {
        String username = authentication.getName();
        List<Prestamo> prestamos = prestamoService.getMisPrestamos(username);
        return ResponseEntity.ok(prestamos);
    }
}
