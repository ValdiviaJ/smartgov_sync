package com.smartgov.sync.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "documentos_ingresados")
public class DocumentoIngresado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDocumento;

    @Column(nullable = false, unique = true)
    private String nroDocumentoUnico;

    @Column(nullable = false)
    private Long idExpediente;

    @Column(nullable = false)
    private Long idTipoDocumento;

    @Column(nullable = false)
    private Long idAdministrado;

    private Integer cantidadFolios;
    private String fechaHoraRecepcion;

    // Multimedia element path
    private String fotoPath;

    private String fechaActualizacion;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        if (fechaActualizacion == null) {
            fechaActualizacion = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }

    public Long getIdDocumento() { return idDocumento; }
    public void setIdDocumento(Long idDocumento) { this.idDocumento = idDocumento; }

    public String getNroDocumentoUnico() { return nroDocumentoUnico; }
    public void setNroDocumentoUnico(String nroDocumentoUnico) { this.nroDocumentoUnico = nroDocumentoUnico; }

    public Long getIdExpediente() { return idExpediente; }
    public void setIdExpediente(Long idExpediente) { this.idExpediente = idExpediente; }

    public Long getIdTipoDocumento() { return idTipoDocumento; }
    public void setIdTipoDocumento(Long idTipoDocumento) { this.idTipoDocumento = idTipoDocumento; }

    public Long getIdAdministrado() { return idAdministrado; }
    public void setIdAdministrado(Long idAdministrado) { this.idAdministrado = idAdministrado; }

    public Integer getCantidadFolios() { return cantidadFolios; }
    public void setCantidadFolios(Integer cantidadFolios) { this.cantidadFolios = cantidadFolios; }

    public String getFechaHoraRecepcion() { return fechaHoraRecepcion; }
    public void setFechaHoraRecepcion(String fechaHoraRecepcion) { this.fechaHoraRecepcion = fechaHoraRecepcion; }

    public String getFotoPath() { return fotoPath; }
    public void setFotoPath(String fotoPath) { this.fotoPath = fotoPath; }

    public String getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(String fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}
