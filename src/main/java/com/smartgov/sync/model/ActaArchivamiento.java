package com.smartgov.sync.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "actas_archivamiento")
public class ActaArchivamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idActa;

    @Column(nullable = false, unique = true)
    private String nroActaUnico;

    @Column(nullable = false)
    private Long idDerivacion;

    @Column(nullable = false)
    private Long idUbicacionArchivo;

    private String fechaHoraGuardado;
    private Double costoDigitalizacion;
    private Double costoArancelCustodia;
    private Double costoFinalProcesamiento;
    private String fechaActualizacion;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        if (fechaActualizacion == null) {
            fechaActualizacion = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }

    public Long getIdActa() { return idActa; }
    public void setIdActa(Long idActa) { this.idActa = idActa; }

    public String getNroActaUnico() { return nroActaUnico; }
    public void setNroActaUnico(String nroActaUnico) { this.nroActaUnico = nroActaUnico; }

    public Long getIdDerivacion() { return idDerivacion; }
    public void setIdDerivacion(Long idDerivacion) { this.idDerivacion = idDerivacion; }

    public Long getIdUbicacionArchivo() { return idUbicacionArchivo; }
    public void setIdUbicacionArchivo(Long idUbicacionArchivo) { this.idUbicacionArchivo = idUbicacionArchivo; }

    public String getFechaHoraGuardado() { return fechaHoraGuardado; }
    public void setFechaHoraGuardado(String fechaHoraGuardado) { this.fechaHoraGuardado = fechaHoraGuardado; }

    public Double getCostoDigitalizacion() { return costoDigitalizacion; }
    public void setCostoDigitalizacion(Double costoDigitalizacion) { this.costoDigitalizacion = costoDigitalizacion; }

    public Double getCostoArancelCustodia() { return costoArancelCustodia; }
    public void setCostoArancelCustodia(Double costoArancelCustodia) { this.costoArancelCustodia = costoArancelCustodia; }

    public Double getCostoFinalProcesamiento() { return costoFinalProcesamiento; }
    public void setCostoFinalProcesamiento(Double costoFinalProcesamiento) { this.costoFinalProcesamiento = costoFinalProcesamiento; }

    public String getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(String fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}
