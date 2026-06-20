package it.uniroma3.siw.calcio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.calcio.model.Tournament;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import it.uniroma3.siw.calcio.model.RankingRow;

public interface TournamentRepository extends JpaRepository<Tournament, Long>{

    boolean existsByNameAndYear(String name, int year);
    boolean existsByNameAndYearAndIdNot(String name, int year, Long id);

    @Query(value = "SELECT t.id AS teamId, t.name AS teamName, " +
           "SUM(CASE " +
           "  WHEN m.state = 'PLAYED' AND m.team_home_id = t.id THEN m.goals_home " +
           "  WHEN m.state = 'PLAYED' AND m.team_away_id = t.id THEN m.goals_away " +
           "  ELSE 0 END) AS goalsFor " +
           "FROM team t " +
           "JOIN tournament_teams tt ON t.id = tt.teams_id " +
           "LEFT JOIN match m ON m.tournament_id = tt.tournaments_id " +
           "                  AND (m.team_home_id = t.id OR m.team_away_id = t.id) " +
           "WHERE tt.tournaments_id = :tournamentId " +
           "GROUP BY t.id, t.name " +
           "ORDER BY goalsFor DESC", nativeQuery = true)
    List<RankingRow> calculateTournamentRanking(@Param("tournamentId") Long tournamentId);

    // EntityGraph per caricare eagerly le partite associate ed evitare query extra nell'HTML
    @EntityGraph(attributePaths = {"matches"})
    @Query("SELECT t FROM Tournament t WHERE t.id = :id")
    Optional<Tournament> findByIdWithMatches(@Param("id") Long id);
}
