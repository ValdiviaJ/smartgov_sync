package com.smartgov.sync.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "archivo_fisico_central")
public class ArchivoFisicoCentral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUbicacion;

    @Column(nullable = false)
    private String codigoAlmacen;

    private Integer nroPabellon;
    private Integer nroEstante;
    private Integer nroCajaFisica;
    private String fechaActualizacion;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        if (fechaActualizacion == null) {
            fechaActualizacion = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }

    public Long getIdUbicacion() { return idUbicacion; }
    public void setIdUbicacion(Long idUbicacion) { this.idUbicacion = idUbicacion; }

    public String getCodigoAlmacen() { return codigoAlmacen; }
    public void setCodigoAlmacen(String codigoAlmacen) { this.codigoAlmacen = codigoAlmacen; }

    public Integer getNroPabellon() { return nroPabellon; }
    public void setNroPabellon(Integer nroPabellon) { this.nroPabellon = nroPabellon; }

    public Integer getNroEstante() { return nroEstante; }
    public void setNroEstante(Integer nroEstante) { this.nroEstante = nroEstante; }

    public Integer getNroCajaFisica() { return nroCajaFisica; }
    public void setNroCajaFisica(Integer nroCajaFisica) { this.nroCajaFisica = nroCajaFisica; }

    public String getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(String fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}
