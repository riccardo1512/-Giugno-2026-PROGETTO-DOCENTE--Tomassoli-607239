package it.uniroma3.siw.calcio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.calcio.model.Referee;

public interface RefereeRepository extends JpaRepository<Referee, Long>{
    
}
