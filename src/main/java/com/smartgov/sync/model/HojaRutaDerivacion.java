package com.smartgov.sync.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "hojas_ruta_derivaciones")
public class HojaRutaDerivacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDerivacion;

    private String codigoBarrasSeguimiento;

    @Column(nullable = false)
    private Long idDocumento;

    @Column(nullable = false)
    private Long idEmpleadoAsignado;

    @Column(nullable = false)
    private Long idOficinaProcedencia;

    private String fechaHoraDespacho;
    private String prioridadEnvio;
    private String fechaHoraRecepcion;
    private String observacionesReceptor;
    private String estadoDerivacion;
    private String fechaActualizacion;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        if (fechaActualizacion == null) {
            fechaActualizacion = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }

    public Long getIdDerivacion() { return idDerivacion; }
    public void setIdDerivacion(Long idDerivacion) { this.idDerivacion = idDerivacion; }

    public String getCodigoBarrasSeguimiento() { return codigoBarrasSeguimiento; }
    public void setCodigoBarrasSeguimiento(String codigoBarrasSeguimiento) { this.codigoBarrasSeguimiento = codigoBarrasSeguimiento; }

    public Long getIdDocumento() { return idDocumento; }
    public void setIdDocumento(Long idDocumento) { this.idDocumento = idDocumento; }

    public Long getIdEmpleadoAsignado() { return idEmpleadoAsignado; }
    public void setIdEmpleadoAsignado(Long idEmpleadoAsignado) { this.idEmpleadoAsignado = idEmpleadoAsignado; }

    public Long getIdOficinaProcedencia() { return idOficinaProcedencia; }
    public void setIdOficinaProcedencia(Long idOficinaProcedencia) { this.idOficinaProcedencia = idOficinaProcedencia; }

    public String getFechaHoraDespacho() { return fechaHoraDespacho; }
    public void setFechaHoraDespacho(String fechaHoraDespacho) { this.fechaHoraDespacho = fechaHoraDespacho; }

    public String getPrioridadEnvio() { return prioridadEnvio; }
    public void setPrioridadEnvio(String prioridadEnvio) { this.prioridadEnvio = prioridadEnvio; }

    public String getFechaHoraRecepcion() { return fechaHoraRecepcion; }
    public void setFechaHoraRecepcion(String fechaHoraRecepcion) { this.fechaHoraRecepcion = fechaHoraRecepcion; }

    public String getObservacionesReceptor() { return observacionesReceptor; }
    public void setObservacionesReceptor(String observacionesReceptor) { this.observacionesReceptor = observacionesReceptor; }

    public String getEstadoDerivacion() { return estadoDerivacion; }
    public void setEstadoDerivacion(String estadoDerivacion) { this.estadoDerivacion = estadoDerivacion; }

    public String getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(String fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}
