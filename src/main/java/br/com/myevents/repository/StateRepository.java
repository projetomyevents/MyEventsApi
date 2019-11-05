package br.com.myevents.repository;

import br.com.myevents.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Responsável pela persistência de {@link State}.
 */
@Repository
public interface StateRepository extends JpaRepository<State, Integer> { }
