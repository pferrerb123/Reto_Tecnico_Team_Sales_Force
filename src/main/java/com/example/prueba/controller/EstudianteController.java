package com.example.prueba.controller;


import com.example.prueba.bean.Estudiante;
import com.example.prueba.business.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @GetMapping
    public List<Estudiante> listarEstudiantes() {
        return estudianteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtenerEstudiantePorId(@PathVariable Integer id) {
        Optional<Estudiante> estudiante = estudianteService.obtenerPorId(id);
        return estudiante.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Estudiante crearEstudiante(@RequestBody Estudiante estudiante) {
        return estudianteService.guardarEstudiante(estudiante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> actualizarEstudiante(@PathVariable Integer id, @RequestBody Estudiante estudianteActualizado) {
        Optional<Estudiante> estudiante = estudianteService.obtenerPorId(id);
        if (estudiante.isPresent()) {
            Estudiante estudianteExistente = estudiante.get();
            estudianteExistente.setNombre(estudianteActualizado.getNombre());
            estudianteExistente.setApellido(estudianteActualizado.getApellido());
            estudianteExistente.setEmail(estudianteActualizado.getEmail());
            estudianteExistente.setCreditos(estudianteActualizado.getCreditos());
            estudianteExistente.setSemestre(estudianteActualizado.getSemestre());
            estudianteExistente.setPromedio(estudianteActualizado.getPromedio());
            estudianteService.actualizarEstudiante(estudianteExistente);
            return ResponseEntity.ok(estudianteExistente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstudiante(@PathVariable Integer id) {
        estudianteService.eliminarEstudiante(id);
        return ResponseEntity.noContent().build();
    }
}
