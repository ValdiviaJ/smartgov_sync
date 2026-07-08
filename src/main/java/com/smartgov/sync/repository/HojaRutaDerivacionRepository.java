package com.smartgov.sync.repository;

import com.smartgov.sync.model.HojaRutaDerivacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HojaRutaDerivacionRepository extends JpaRepository<HojaRutaDerivacion, Long> {
    List<HojaRutaDerivacion> findByFechaActualizacionGreaterThan(String timestamp);
}
