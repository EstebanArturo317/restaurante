
package com.casadonagloria.restaurante.repository;

import com.casadonagloria.restaurante.model.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Integer> {

}
