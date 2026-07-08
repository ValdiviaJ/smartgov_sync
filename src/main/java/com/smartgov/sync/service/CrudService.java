package com.smartgov.sync.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartgov.sync.model.*;
import com.smartgov.sync.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CrudService {

    @Autowired private OficinaRepository oficinaRepository;
    @Autowired private PersonalEspecialistaRepository specialistRepository;
    @Autowired private TipoDocumentoRepository tipoDocumentoRepository;
    @Autowired private AdministradoRepository administradoRepository;
    @Autowired private AdministradoDireccionRepository administradoDireccionRepository;
    @Autowired private ExpedienteGeneralRepository expedienteGeneralRepository;
    @Autowired private DocumentoIngresadoRepository documentoIngresadoRepository;
    @Autowired private HojaRutaDerivacionRepository hojaRutaDerivacionRepository;
    @Autowired private ArchivoFisicoCentralRepository archivoFisicoCentralRepository;
    @Autowired private ActaArchivamientoRepository actaArchivamientoRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    @Autowired private ObjectMapper objectMapper;

    private final Map<String, JpaRepository<?, Long>> repositoryMap = new HashMap<>();
    private final Map<String, Class<?>> classMap = new HashMap<>();

    @PostConstruct
    public void init() {
        repositoryMap.put("oficinas", oficinaRepository);
        repositoryMap.put("personal_especialistas", specialistRepository);
        repositoryMap.put("tipos_documentos", tipoDocumentoRepository);
        repositoryMap.put("administrados", administradoRepository);
        repositoryMap.put("administrado_direcciones", administradoDireccionRepository);
        repositoryMap.put("expedientes_generales", expedienteGeneralRepository);
        repositoryMap.put("documentos_ingresados", documentoIngresadoRepository);
        repositoryMap.put("hojas_ruta_derivaciones", hojaRutaDerivacionRepository);
        repositoryMap.put("archivo_fisico_central", archivoFisicoCentralRepository);
        repositoryMap.put("actas_archivamiento", actaArchivamientoRepository);
        repositoryMap.put("usuarios", usuarioRepository);

        classMap.put("oficinas", Oficina.class);
        classMap.put("personal_especialistas", PersonalEspecialista.class);
        classMap.put("tipos_documentos", TipoDocumento.class);
        classMap.put("administrados", Administrado.class);
        classMap.put("administrado_direcciones", AdministradoDireccion.class);
        classMap.put("expedientes_generales", ExpedienteGeneral.class);
        classMap.put("documentos_ingresados", DocumentoIngresado.class);
        classMap.put("hojas_ruta_derivaciones", HojaRutaDerivacion.class);
        classMap.put("archivo_fisico_central", ArchivoFisicoCentral.class);
        classMap.put("actas_archivamiento", ActaArchivamiento.class);
        classMap.put("usuarios", Usuario.class);
    }

    public boolean isValidTable(String table) {
        return repositoryMap.containsKey(table);
    }

    public List<?> findAll(String table) {
        return repositoryMap.get(table).findAll();
    }

    public Optional<?> findById(String table, Long id) {
        return repositoryMap.get(table).findById(id);
    }

    public Object save(String table, Map<String, Object> payload) throws Exception {
        JpaRepository<Object, Long> repo = (JpaRepository<Object, Long>) repositoryMap.get(table);
        Class<?> clazz = classMap.get(table);

        if (!payload.containsKey("fechaActualizacion")) {
            payload.put("fechaActualizacion", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }

        Object entity = objectMapper.convertValue(payload, clazz);
        return repo.save(entity);
    }

    public Object update(String table, Long id, Map<String, Object> payload) throws Exception {
        JpaRepository<Object, Long> repo = (JpaRepository<Object, Long>) repositoryMap.get(table);
        Class<?> clazz = classMap.get(table);

        String idFieldName = getIdFieldName(table);
        payload.put(idFieldName, id);
        payload.put("fechaActualizacion", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        Object entity = objectMapper.convertValue(payload, clazz);
        return repo.save(entity);
    }

    public void delete(String table, Long id) {
        repositoryMap.get(table).deleteById(id);
    }

    public boolean exists(String table, Long id) {
        return repositoryMap.get(table).existsById(id);
    }

    private String getIdFieldName(String table) {
        switch (table) {
            case "oficinas": return "idOficina";
            case "personal_especialistas": return "idEmpleado";
            case "tipos_documentos": return "idTipoDocumento";
            case "administrados": return "idAdministrado";
            case "administrado_direcciones": return "idDireccion";
            case "expedientes_generales": return "idExpediente";
            case "documentos_ingresados": return "idDocumento";
            case "hojas_ruta_derivaciones": return "idDerivacion";
            case "archivo_fisico_central": return "idUbicacion";
            case "actas_archivamiento": return "idActa";
            case "usuarios": return "idUsuario";
            default: return "id";
        }
    }
}
