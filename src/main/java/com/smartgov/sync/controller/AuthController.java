package com.smartgov.sync.controller;

import com.smartgov.sync.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if (username == null || password == null) {
            Map<String, String> err = new HashMap<>();
            err.put("error", "Usuario y contraseña son requeridos.");
            return ResponseEntity.badRequest().body(err);
        }

        Optional<Map<String, Object>> authResponse = authService.authenticate(username, password);
        if (authResponse.isEmpty()) {
            Map<String, String> err = new HashMap<>();
            err.put("error", "Credenciales inválidas.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
        }

        return ResponseEntity.ok(authResponse.get());
    }
}
