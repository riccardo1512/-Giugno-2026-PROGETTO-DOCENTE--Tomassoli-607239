package it.uniroma3.siw.calcio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.calcio.exception.DuplicateTeamException;
import it.uniroma3.siw.calcio.model.Team;
import it.uniroma3.siw.calcio.repository.TeamRepository;
import it.uniroma3.siw.calcio.repository.MatchRepository;
import it.uniroma3.siw.calcio.repository.PlayerRepository;
import it.uniroma3.siw.calcio.repository.TournamentRepository;
import it.uniroma3.siw.calcio.model.Match;
import it.uniroma3.siw.calcio.model.Player;
import it.uniroma3.siw.calcio.model.Tournament;

@Service
public class TeamService {

   private final TeamRepository teamRepository;
   private final MatchRepository matchRepository;
   private final PlayerRepository playerRepository;
   private final TournamentRepository tournamentRepository;

    public TeamService(TeamRepository teamRepository, MatchRepository matchRepository, PlayerRepository playerRepository, TournamentRepository tournamentRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
        this.tournamentRepository = tournamentRepository;
    }

    @Transactional(readOnly = true)
    public List<Team> findAll() {
        return this.teamRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Team> findById(Long id) {
        Optional<Team> teamOpt = this.teamRepository.findById(id);
        if (teamOpt.isPresent()) {
            Team team = teamOpt.get();
            if (team.getPlayers() != null) team.getPlayers().size(); // Inizializza la lista
            if (team.getMatchesHome() != null) team.getMatchesHome().size();
            if (team.getMatchesAway() != null) team.getMatchesAway().size();
        }
        return teamOpt;
    }

    @Transactional
    public Team save(Team team) throws DuplicateTeamException {
        boolean duplicate = team.getId() == null
            ? this.teamRepository.existsByNameAndYearOfFoundation(team.getName(), team.getYearOfFoundation())
            : this.teamRepository.existsByNameAndYearOfFoundationAndIdNot(team.getName(), team.getYearOfFoundation(), team.getId());
        if (duplicate) {
            throw new DuplicateTeamException(team.getName(), team.getYearOfFoundation());
        }
        return this.teamRepository.save(team);
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Team> teamOpt = this.teamRepository.findById(id);
        if (teamOpt.isPresent()) {
            Team team = teamOpt.get();
            
            // 1. Rimuovi la squadra dai tornei
            for (Tournament t : team.getTournaments()) {
                t.getTeams().remove(team);
                tournamentRepository.save(t);
            }
            
            // 2. Svinciliamo (o eliminiamo) i giocatori
            for (Player p : team.getPlayers()) {
                p.setTeam(null);
                playerRepository.save(p);
            }
            
            // 3. Eliminiamo tutte le partite giocate da questa squadra
            List<Match> matchesH = matchRepository.findByTeamHome(team);
            matchRepository.deleteAll(matchesH);
            List<Match> matchesA = matchRepository.findByTeamAway(team);
            matchRepository.deleteAll(matchesA);

            // 4. Infine eliminiamo la squadra
            this.teamRepository.delete(team);
        }
    }
}
