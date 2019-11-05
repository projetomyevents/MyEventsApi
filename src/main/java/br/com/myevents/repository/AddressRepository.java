package br.com.myevents.repository;

import br.com.myevents.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Responsável pela persistência de {@link Address}.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> { }
