package com.library.service;

import com.library.entity.Libro;
import com.library.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> getAllLibros() {
        return libroRepository.findAll();
    }

    @Cacheable(value = "libros", key = "#id")
    public Libro getLibroById(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con id: " + id));
    }

    @Transactional
    public Libro createLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    @Transactional
    @CacheEvict(value = "libros", key = "#id")
    public Libro updateLibro(Long id, Libro libroDetails) {
        Libro libro = getLibroById(id);
        libro.setTitulo(libroDetails.getTitulo());
        libro.setAutor(libroDetails.getAutor());
        libro.setIsbn(libroDetails.getIsbn());
        libro.setDisponible(libroDetails.isDisponible());
        return libroRepository.save(libro);
    }
}
