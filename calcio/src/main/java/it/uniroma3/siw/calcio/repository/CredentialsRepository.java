package it.uniroma3.siw.calcio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.calcio.model.Credentials;

public interface CredentialsRepository extends JpaRepository<Credentials, Long> {

	Credentials findByUsername(String username);

}
