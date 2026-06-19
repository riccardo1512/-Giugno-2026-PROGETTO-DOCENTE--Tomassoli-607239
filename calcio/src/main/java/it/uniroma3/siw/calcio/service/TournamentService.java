package it.uniroma3.siw.calcio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.calcio.model.Tournament;
import it.uniroma3.siw.calcio.repository.TournamentRepository;

@Service
public class TournamentService {
    private final TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    @Transactional
    public Tournament save(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    @Transactional(readOnly = true)
    public Optional<Tournament> findById(Long id) {
        Optional<Tournament> tournamentOpt = this.tournamentRepository.findById(id);
        if (tournamentOpt.isPresent()) {
            Tournament tournament = tournamentOpt.get();
            if (tournament.getTeams() != null) tournament.getTeams().size();
            if (tournament.getMatches() != null) tournament.getMatches().size();
        }
        return tournamentOpt;
    }

    @Transactional(readOnly = true)
    public List<Tournament> findAll() {
        return (List<Tournament>) tournamentRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        tournamentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<it.uniroma3.siw.calcio.model.RankingRow> getTournamentRanking(Long tournamentId) {
        return tournamentRepository.calculateTournamentRanking(tournamentId);
    }
}
