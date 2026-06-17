package it.uniroma3.siw.calcio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.calcio.model.Match;
import it.uniroma3.siw.calcio.model.Team;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long>{
    List<Match> findByTeamHome(Team teamHome);
    List<Match> findByTeamAway(Team teamAway);
}
