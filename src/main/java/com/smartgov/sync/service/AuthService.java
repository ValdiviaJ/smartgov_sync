package com.smartgov.sync.service;

import com.smartgov.sync.model.Oficina;
import com.smartgov.sync.model.PersonalEspecialista;
import com.smartgov.sync.model.Usuario;
import com.smartgov.sync.repository.OficinaRepository;
import com.smartgov.sync.repository.PersonalEspecialistaRepository;
import com.smartgov.sync.repository.UsuarioRepository;
import com.smartgov.sync.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PersonalEspecialistaRepository specialistRepository;

    @Autowired
    private OficinaRepository oficinaRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Optional<Map<String, Object>> authenticate(String username, String password) {
        Optional<Usuario> userOpt = usuarioRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        Usuario user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return Optional.empty();
        }

        String nombreCompleto = "";
        String codigoEmpleado = "";
        Long idOficina = null;
        String nombreOficina = "";

        if (user.getIdEmpleado() != null) {
            Optional<PersonalEspecialista> specialistOpt = specialistRepository.findById(user.getIdEmpleado());
            if (specialistOpt.isPresent()) {
                PersonalEspecialista specialist = specialistOpt.get();
                nombreCompleto = specialist.getNombreCompleto();
                codigoEmpleado = specialist.getCodigoEmpleado();
                idOficina = specialist.getIdOficina();

                if (idOficina != null) {
                    Optional<Oficina> officeOpt = oficinaRepository.findById(idOficina);
                    if (officeOpt.isPresent()) {
                        nombreOficina = officeOpt.get().getNombreUnidad();
                    }
                }
            }
        }

        // Add additional claims to JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("id_usuario", user.getIdUsuario());
        claims.put("username", user.getUsername());
        claims.put("id_empleado", user.getIdEmpleado());
        claims.put("nombre_completo", nombreCompleto);
        claims.put("id_oficina", idOficina);
        claims.put("nombre_oficina", nombreOficina);

        String token = jwtTokenUtil.generateToken(username, claims);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Inicio de sesión exitoso");
        response.put("token", token);

        Map<String, Object> userData = new HashMap<>();
        userData.put("id_usuario", user.getIdUsuario());
        userData.put("username", user.getUsername());
        userData.put("id_empleado", user.getIdEmpleado());
        userData.put("nombre_completo", nombreCompleto);
        userData.put("id_oficina", idOficina);
        userData.put("nombre_oficina", nombreOficina);
        response.put("user", userData);

        return Optional.of(response);
    }
}
