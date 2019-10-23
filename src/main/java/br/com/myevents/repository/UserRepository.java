package br.com.myevents.repository;

import br.com.myevents.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A classe responsável pela persistência de {@link User}.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> { }
