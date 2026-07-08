package com.smartgov.sync.controller;

import com.smartgov.sync.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CrudController {

    @Autowired
    private CrudService crudService;

    // 1. GET ALL
    @GetMapping("/{table}")
    public ResponseEntity<?> getAll(@PathVariable String table) {
        if (!crudService.isValidTable(table)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "La entidad '" + table + "' no existe."));
        }
        return ResponseEntity.ok(crudService.findAll(table));
    }

    // 2. GET BY ID
    @GetMapping("/{table}/{id}")
    public ResponseEntity<?> getById(@PathVariable String table, @PathVariable Long id) {
        if (!crudService.isValidTable(table)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "La entidad '" + table + "' no existe."));
        }
        Optional<?> entity = crudService.findById(table, id);
        if (entity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Registro no encontrado en " + table + " con ID " + id));
        }
        return ResponseEntity.ok(entity.get());
    }

    // 3. CREATE
    @PostMapping("/{table}")
    public ResponseEntity<?> create(@PathVariable String table, @RequestBody Map<String, Object> payload) {
        if (!crudService.isValidTable(table)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "La entidad '" + table + "' no existe."));
        }

        try {
            Object saved = crudService.save(table, payload);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al crear registro: " + e.getMessage()));
        }
    }

    // 4. UPDATE
    @PutMapping("/{table}/{id}")
    public ResponseEntity<?> update(@PathVariable String table, @PathVariable Long id, @RequestBody Map<String, Object> payload) {
        if (!crudService.isValidTable(table)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "La entidad '" + table + "' no existe."));
        }

        if (!crudService.exists(table, id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Registro no encontrado en " + table + " con ID " + id));
        }

        try {
            Object saved = crudService.update(table, id, payload);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al actualizar registro: " + e.getMessage()));
        }
    }

    // 5. DELETE
    @DeleteMapping("/{table}/{id}")
    public ResponseEntity<?> delete(@PathVariable String table, @PathVariable Long id) {
        if (!crudService.isValidTable(table)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "La entidad '" + table + "' no existe."));
        }
        if (!crudService.exists(table, id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Registro no encontrado en " + table + " con ID " + id));
        }
        crudService.delete(table, id);
        return ResponseEntity.ok(Map.of("message", "Registro con ID " + id + " eliminado exitosamente de " + table));
    }
}
