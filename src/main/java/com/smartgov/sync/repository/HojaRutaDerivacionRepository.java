package com.smartgov.sync.repository;

import com.smartgov.sync.model.HojaRutaDerivacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HojaRutaDerivacionRepository extends JpaRepository<HojaRutaDerivacion, Long> {
    @Query("SELECT h FROM HojaRutaDerivacion h WHERE h.fechaActualizacion IS NULL OR h.fechaActualizacion > ?1")
    List<HojaRutaDerivacion> findByFechaActualizacionGreaterThan(String timestamp);
}
