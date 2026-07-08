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
import java.util.*;

@Service
public class SyncService {

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
    }

    public Map<String, Object> pullUpdates(String lastSyncTimestamp) {
        String lastSync = (lastSyncTimestamp != null && !lastSyncTimestamp.trim().isEmpty()) ? lastSyncTimestamp : "1970-01-01 00:00:00";
        Map<String, Object> data = new HashMap<>();

        data.put("oficinas", oficinaRepository.findByFechaActualizacionGreaterThan(lastSync));
        data.put("personal_especialistas", specialistRepository.findByFechaActualizacionGreaterThan(lastSync));
        data.put("tipos_documentos", tipoDocumentoRepository.findByFechaActualizacionGreaterThan(lastSync));
        data.put("administrados", administradoRepository.findByFechaActualizacionGreaterThan(lastSync));
        data.put("administrado_direcciones", administradoDireccionRepository.findByFechaActualizacionGreaterThan(lastSync));
        data.put("expedientes_generales", expedienteGeneralRepository.findByFechaActualizacionGreaterThan(lastSync));
        data.put("documentos_ingresados", documentoIngresadoRepository.findByFechaActualizacionGreaterThan(lastSync));
        data.put("hojas_ruta_derivaciones", hojaRutaDerivacionRepository.findByFechaActualizacionGreaterThan(lastSync));
        data.put("archivo_fisico_central", archivoFisicoCentralRepository.findByFechaActualizacionGreaterThan(lastSync));
        data.put("actas_archivamiento", actaArchivamientoRepository.findByFechaActualizacionGreaterThan(lastSync));

        return data;
    }

    public Map<String, Map<String, Object>> pushUpdates(Map<String, List<Map<String, Object>>> syncRecords, String currentTime) {
        Map<String, Map<String, Object>> results = new HashMap<>();

        for (String table : syncRecords.keySet()) {
            JpaRepository<Object, Long> repo = (JpaRepository<Object, Long>) repositoryMap.get(table);
            Class<?> clazz = classMap.get(table);

            if (repo == null || clazz == null) {
                continue;
            }

            String pkName = getIdFieldName(table);
            List<Map<String, Object>> records = syncRecords.get(table);

            int inserted = 0;
            int updated = 0;
            List<Map<String, Object>> errors = new ArrayList<>();

            for (Map<String, Object> record : records) {
                try {
                    Object idValObj = record.get(pkName);
                    Long pkValue = idValObj != null ? Long.valueOf(idValObj.toString()) : null;

                    Optional<Object> existingOpt = Optional.empty();
                    if (pkValue != null) {
                        existingOpt = repo.findById(pkValue);
                    }

                    if (!record.containsKey("fechaActualizacion") || record.get("fechaActualizacion") == null) {
                        record.put("fechaActualizacion", currentTime);
                    }

                    if (existingOpt.isPresent()) {
                        Object existing = existingOpt.get();
                        String serverTime = (String) getFieldValue(existing, "fechaActualizacion");
                        String incomingTime = (String) record.get("fechaActualizacion");

                        if (serverTime == null) serverTime = "1970-01-01 00:00:00";

                        if (incomingTime.compareTo(serverTime) >= 0) {
                            Object entity = objectMapper.convertValue(record, clazz);
                            repo.save(entity);
                            updated++;
                        } else {
                            Map<String, Object> conflictErr = new HashMap<>();
                            conflictErr.put("id", pkValue);
                            conflictErr.put("message", "Conflicto resuelto: El servidor tiene una versión más reciente.");
                            errors.add(conflictErr);
                        }
                    } else {
                        Object entity = objectMapper.convertValue(record, clazz);
                        repo.save(entity);
                        inserted++;
                    }

                } catch (Exception re) {
                    Map<String, Object> err = new HashMap<>();
                    err.put("record", record);
                    err.put("message", re.getMessage());
                    errors.add(err);
                }
            }

            Map<String, Object> tableSummary = new HashMap<>();
            tableSummary.put("inserted", inserted);
            tableSummary.put("updated", updated);
            tableSummary.put("errors", errors);
            results.put(table, tableSummary);
        }

        return results;
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
            default: return "id";
        }
    }

    private Object getFieldValue(Object obj, String fieldName) {
        try {
            java.lang.reflect.Method method = obj.getClass().getMethod("get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1));
            return method.invoke(obj);
        } catch (Exception e) {
            return null;
        }
    }
}
