package it.uniroma3.siw.calcio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.calcio.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{

    //List<Team> findByTitleContainingIgnoreCase(String title);

    boolean existsByNameAndYearOfFoundation(String name, Integer yearOfFoundation);

    boolean existsByNameAndYearOfFoundationAndIdNot(String title, Integer year, Long id);
    
}
