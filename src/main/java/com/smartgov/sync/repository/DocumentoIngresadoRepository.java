package com.smartgov.sync.repository;

import com.smartgov.sync.model.DocumentoIngresado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DocumentoIngresadoRepository extends JpaRepository<DocumentoIngresado, Long> {
    List<DocumentoIngresado> findByFechaActualizacionGreaterThan(String timestamp);

    @Modifying
    @Transactional
    @Query("UPDATE DocumentoIngresado d SET d.fotoPath = :fotoPath, d.fechaActualizacion = :fechaActualizacion WHERE d.idDocumento = :id")
    int actualizarFotoPath(@Param("id") Long id, @Param("fotoPath") String fotoPath, @Param("fechaActualizacion") String fechaActualizacion);
}
