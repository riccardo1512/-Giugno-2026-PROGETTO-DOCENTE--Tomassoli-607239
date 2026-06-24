package it.uniroma3.siw.calcio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

import it.uniroma3.siw.calcio.model.Match;
import it.uniroma3.siw.calcio.model.Team;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long>{
    List<Match> findByTeamHome(Team teamHome);
    List<Match> findByTeamAway(Team teamAway);

    // Ottimizzazione: JOIN FETCH multipli su associazioni singole per evitare query N+1 nella vista
    @Query("SELECT m FROM Match m JOIN FETCH m.teamHome JOIN FETCH m.teamAway LEFT JOIN FETCH m.referee WHERE m.id = :id")
    Optional<Match> findByIdWithDetails(@Param("id") Long id);

    @Query("SELECT m FROM Match m JOIN FETCH m.teamHome JOIN FETCH m.teamAway LEFT JOIN FETCH m.referee")
    List<Match> findAllWithDetails();
}
