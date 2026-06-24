package it.uniroma3.siw.calcio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.calcio.model.Player;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long>{

    
    boolean existsByNameAndSurname(String name, String surname);

    boolean existsByNameAndSurnameAndIdNot(String name, String surname, Long id);

    /**
     * Risolve il problema N+1 nella lista dei giocatori causato dal fetch di default EAGER,
     * anche se poi in realtà non viene stampata la squadra nella vista.
     */
    @EntityGraph(attributePaths = {"team"})
    @Query("SELECT p FROM Player p")
    List<Player> findAllWithTeam();
    
}
