package it.uniroma3.siw.calcio.runner;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import it.uniroma3.siw.calcio.model.Player;
import it.uniroma3.siw.calcio.model.Team;
import it.uniroma3.siw.calcio.repository.PlayerRepository;
import it.uniroma3.siw.calcio.repository.TeamRepository;

/**
 * Analisi sperimentale sulle strategie di fetch (Requisito 8.2)
 * Confronto tra caricamento LAZY (N+1 query) e JOIN FETCH ottimizzato.
 * 
 * Risultati misurati:
 * - Tempo esecuzione con LAZY: 0.223048099 seconds
 * - Tempo esecuzione con JOIN FETCH: 0.030758899 seconds
 */
// Rimuovere il commento per far partire il test all'avvio
// @Component
public class PerformanceExperimentRunner implements CommandLineRunner {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public PerformanceExperimentRunner(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println(">>> INIZIO ANALISI SPERIMENTALE FETCH STRATEGIES <<<");

        // 1. Setup dei dati (solo se il DB è vuoto o ha pochi team)
        setupDummyData();

        // 2. Test strategia LAZY (N+1 Query Problem)
        testLazyStrategy();

        // 3. Test strategia JOIN FETCH (Ottimizzata)
        testJoinFetchStrategy();

        System.out.println(">>> FINE ANALISI SPERIMENTALE FETCH STRATEGIES <<<");
    }

    private void setupDummyData() {
        if (teamRepository.count() < 20) {
            System.out.println("--- Setup dati sperimentali in corso... ---");
            for (int i = 1; i <= 20; i++) {
                Team team = new Team();
                team.setName("Test Team " + i);
                team.setYearOfFoundation(1900 + i);
                team.setCity("Test City " + i);
                teamRepository.save(team);

                for (int j = 1; j <= 20; j++) {
                    Player p = new Player();
                    p.setName("Player " + j);
                    p.setSurname("Surname " + i);
                    p.setTeam(team);
                    playerRepository.save(p);
                }
            }
        }
    }

    private void testLazyStrategy() {
        System.out.println("--- Esecuzione Test: LAZY FETCH (N+1 Query) ---");
        StopWatch watch = new StopWatch();
        watch.start("Lazy Fetch - teamRepository.findAll()");

        // Carica solo i team (genera N+1 query nel ciclo successivo)
        List<Team> teams = teamRepository.findAll();

        int totalPlayers = 0;
        // Accedendo alla lista giocatori scatta il lazy loading

        for (Team team : teams) {
            if (team.getPlayers() != null) {
                totalPlayers += team.getPlayers().size(); // Triggera la query Lazy
            }
        }

        watch.stop();
        System.out.println("Risultato: Trovati " + totalPlayers + " giocatori.");
        System.out.println(watch.prettyPrint());
    }

    private void testJoinFetchStrategy() {
        System.out.println("--- Esecuzione Test: JOIN FETCH (Ottimizzata) ---");
        StopWatch watch = new StopWatch();
        watch.start("Join Fetch - teamRepository.findAllWithPlayers()");

        // Carica team e giocatori in una singola query SQL
        List<Team> teams = teamRepository.findAllWithPlayers();

        int totalPlayers = 0;
        // I dati sono già in memoria, nessuna query extra
        for (Team team : teams) {
            if (team.getPlayers() != null) {
                totalPlayers += team.getPlayers().size(); // Istantaneo (già caricato)
            }
        }

        watch.stop();
        System.out.println("Risultato: Trovati " + totalPlayers + " giocatori.");
        System.out.println(watch.prettyPrint());
    }
}
