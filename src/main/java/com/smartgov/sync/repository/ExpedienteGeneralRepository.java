package com.smartgov.sync.repository;

import com.smartgov.sync.model.ExpedienteGeneral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpedienteGeneralRepository extends JpaRepository<ExpedienteGeneral, Long> {
    List<ExpedienteGeneral> findByFechaActualizacionGreaterThan(String timestamp);
}
