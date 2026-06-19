package it.uniroma3.siw.calcio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.calcio.model.Team;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamRepository extends JpaRepository<Team, Long>{

    //List<Team> findByTitleContainingIgnoreCase(String title);

    boolean existsByNameAndYearOfFoundation(String name, Integer yearOfFoundation);

    boolean existsByNameAndYearOfFoundationAndIdNot(String title, Integer year, Long id);
    
    @Query("SELECT DISTINCT t FROM Team t JOIN FETCH t.players")
    List<Team> findAllWithPlayers();

    // Risolve il problema 1+N nella pagina di dettaglio recuperando i giocatori eagerly
    @EntityGraph(attributePaths = {"players"})
    @Query("SELECT t FROM Team t WHERE t.id = :id")
    Optional<Team> findByIdWithPlayers(@Param("id") Long id);
}
