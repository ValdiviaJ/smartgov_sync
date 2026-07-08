package com.smartgov.sync.repository;

import com.smartgov.sync.model.DocumentoIngresado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoIngresadoRepository extends JpaRepository<DocumentoIngresado, Long> {
    List<DocumentoIngresado> findByFechaActualizacionGreaterThan(String timestamp);
}
