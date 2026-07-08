package com.smartgov.sync.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "administrados")
public class Administrado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdministrado;

    @Column(nullable = false)
    private String codigoAdministrado;

    @Column(nullable = false, unique = true)
    private String dniRuc;

    @Column(nullable = false)
    private String nombreRazonSocial;

    private String telefono;
    private String correoNotificaciones;
    private String fechaActualizacion;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        if (fechaActualizacion == null) {
            fechaActualizacion = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }

    public Long getIdAdministrado() { return idAdministrado; }
    public void setIdAdministrado(Long idAdministrado) { this.idAdministrado = idAdministrado; }

    public String getCodigoAdministrado() { return codigoAdministrado; }
    public void setCodigoAdministrado(String codigoAdministrado) { this.codigoAdministrado = codigoAdministrado; }

    public String getDniRuc() { return dniRuc; }
    public void setDniRuc(String dniRuc) { this.dniRuc = dniRuc; }

    public String getNombreRazonSocial() { return nombreRazonSocial; }
    public void setNombreRazonSocial(String nombreRazonSocial) { this.nombreRazonSocial = nombreRazonSocial; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreoNotificaciones() { return correoNotificaciones; }
    public void setCorreoNotificaciones(String correoNotificaciones) { this.correoNotificaciones = correoNotificaciones; }

    public String getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(String fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}
