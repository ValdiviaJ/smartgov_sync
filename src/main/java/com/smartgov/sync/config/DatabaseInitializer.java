package com.smartgov.sync.config;

import com.smartgov.sync.model.*;
import com.smartgov.sync.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private OficinaRepository oficinaRepository;

    @Autowired
    private PersonalEspecialistaRepository specialistRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Checking and seeding database initial data...");

        // 1. Seed Oficinas
        if (oficinaRepository.count() == 0) {
            Oficina o1 = new Oficina();
            o1.setCodigoOficina("OF-MP");
            o1.setSiglasOficiales("MP");
            o1.setNombreUnidad("Mesa de Partes");
            oficinaRepository.save(o1);

            Oficina o2 = new Oficina();
            o2.setCodigoOficina("OF-AL");
            o2.setSiglasOficiales("AL");
            o2.setNombreUnidad("Asesoría Legal");
            oficinaRepository.save(o2);

            Oficina o3 = new Oficina();
            o3.setCodigoOficina("OF-PP");
            o3.setSiglasOficiales("PP");
            o3.setNombreUnidad("Planeamiento y Presupuesto");
            oficinaRepository.save(o3);
        }

        // 2. Seed Personal Especialista
        if (specialistRepository.count() == 0) {
            PersonalEspecialista p1 = new PersonalEspecialista();
            p1.setCodigoEmpleado("EMP001");
            p1.setNombreCompleto("Juan Pérez Alva");
            p1.setIdOficina(1L);
            specialistRepository.save(p1);

            PersonalEspecialista p2 = new PersonalEspecialista();
            p2.setCodigoEmpleado("EMP002");
            p2.setNombreCompleto("María Gómez Sifuentes");
            p2.setIdOficina(2L);
            specialistRepository.save(p2);
        }

        // 3. Seed Usuarios
        if (usuarioRepository.count() == 0) {
            Usuario u1 = new Usuario();
            u1.setUsername("admin");
            u1.setPassword(passwordEncoder.encode("admin123"));
            u1.setIdEmpleado(1L);
            usuarioRepository.save(u1);

            Usuario u2 = new Usuario();
            u2.setUsername("user");
            u2.setPassword(passwordEncoder.encode("user123"));
            u2.setIdEmpleado(2L);
            usuarioRepository.save(u2);
        }

        // 4. Seed Tipos Documento
        if (tipoDocumentoRepository.count() == 0) {
            String[] types = {"Carta", "Oficio", "Solicitud", "Informe", "Resolución"};
            for (String t : types) {
                TipoDocumento td = new TipoDocumento();
                td.setNombreTipoDocumento(t);
                tipoDocumentoRepository.save(td);
            }
        }

        System.out.println("Database seeding completed.");
    }
}
