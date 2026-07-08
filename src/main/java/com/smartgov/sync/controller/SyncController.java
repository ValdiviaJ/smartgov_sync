package com.smartgov.sync.controller;

import com.smartgov.sync.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SyncController {

    @Autowired
    private SyncService syncService;

    // GET /api/sincronizacion (PULL)
    @GetMapping("/sincronizacion")
    public ResponseEntity<?> pull(@RequestParam(value = "last_sync_timestamp", required = false) String lastSyncTimestamp) {
        try {
            Map<String, Object> data = syncService.pullUpdates(lastSyncTimestamp);
            Map<String, Object> response = new HashMap<>();
            response.put("server_time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            response.put("data", data);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al recuperar actualizaciones: " + e.getMessage()));
        }
    }

    // POST /api/sync-data (PUSH)
    @PostMapping("/sync-data")
    public ResponseEntity<?> push(@RequestBody Map<String, Object> requestBody) {
        Map<String, List<Map<String, Object>>> syncRecords = (Map<String, List<Map<String, Object>>>) requestBody.get("sync_records");

        if (syncRecords == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Formato de sincronización inválido."));
        }

        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        try {
            Map<String, Map<String, Object>> results = syncService.pushUpdates(syncRecords, currentTime);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Sincronización de subida (Push) completada.");
            response.put("timestamp", currentTime);
            response.put("results", results);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error general en sincronización: " + e.getMessage()));
        }
    }
}
