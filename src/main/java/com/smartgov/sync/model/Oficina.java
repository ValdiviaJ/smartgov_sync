package com.smartgov.sync.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "oficinas")
public class Oficina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOficina;

    @Column(nullable = false)
    private String codigoOficina;

    private String siglasOficiales;

    @Column(nullable = false)
    private String nombreUnidad;

    private String fechaActualizacion;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        if (fechaActualizacion == null) {
            fechaActualizacion = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }

    // Getters and Setters
    public Long getIdOficina() { return idOficina; }
    public void setIdOficina(Long idOficina) { this.idOficina = idOficina; }

    public String getCodigoOficina() { return codigoOficina; }
    public void setCodigoOficina(String codigoOficina) { this.codigoOficina = codigoOficina; }

    public String getSiglasOficiales() { return siglasOficiales; }
    public void setSiglasOficiales(String siglasOficiales) { this.siglasOficiales = siglasOficiales; }

    public String getNombreUnidad() { return nombreUnidad; }
    public void setNombreUnidad(String nombreUnidad) { this.nombreUnidad = nombreUnidad; }

    public String getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(String fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}
