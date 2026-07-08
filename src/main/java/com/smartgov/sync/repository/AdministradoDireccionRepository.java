package com.smartgov.sync.repository;

import com.smartgov.sync.model.AdministradoDireccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministradoDireccionRepository extends JpaRepository<AdministradoDireccion, Long> {
    List<AdministradoDireccion> findByFechaActualizacionGreaterThan(String timestamp);
}
