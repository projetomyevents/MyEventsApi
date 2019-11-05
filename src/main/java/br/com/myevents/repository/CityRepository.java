package br.com.myevents.repository;

import br.com.myevents.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Responsável pela persistência de {@link City}.
 */
@Repository
public interface CityRepository extends JpaRepository<City, Integer> { }
