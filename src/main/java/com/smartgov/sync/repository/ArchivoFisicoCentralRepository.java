package com.smartgov.sync.repository;

import com.smartgov.sync.model.ArchivoFisicoCentral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchivoFisicoCentralRepository extends JpaRepository<ArchivoFisicoCentral, Long> {
    List<ArchivoFisicoCentral> findByFechaActualizacionGreaterThan(String timestamp);
}
