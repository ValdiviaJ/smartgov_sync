package com.smartgov.sync.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "expedientes_generales")
public class ExpedienteGeneral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExpediente;

    @Column(nullable = false, unique = true)
    private String nroExpedienteAnual;

    private String fechaHoraApertura;
    private String asuntoGeneral;
    private String estadoGlobal;
    private String fechaActualizacion;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        if (fechaActualizacion == null) {
            fechaActualizacion = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }

    public Long getIdExpediente() { return idExpediente; }
    public void setIdExpediente(Long idExpediente) { this.idExpediente = idExpediente; }

    public String getNroExpedienteAnual() { return nroExpedienteAnual; }
    public void setNroExpedienteAnual(String nroExpedienteAnual) { this.nroExpedienteAnual = nroExpedienteAnual; }

    public String getFechaHoraApertura() { return fechaHoraApertura; }
    public void setFechaHoraApertura(String fechaHoraApertura) { this.fechaHoraApertura = fechaHoraApertura; }

    public String getAsuntoGeneral() { return asuntoGeneral; }
    public void setAsuntoGeneral(String asuntoGeneral) { this.asuntoGeneral = asuntoGeneral; }

    public String getEstadoGlobal() { return estadoGlobal; }
    public void setEstadoGlobal(String estadoGlobal) { this.estadoGlobal = estadoGlobal; }

    public String getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(String fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}
