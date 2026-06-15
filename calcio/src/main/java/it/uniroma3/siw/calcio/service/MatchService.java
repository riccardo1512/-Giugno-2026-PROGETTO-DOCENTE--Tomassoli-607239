package it.uniroma3.siw.calcio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.calcio.model.Match;
import it.uniroma3.siw.calcio.repository.MatchRepository;

@Service
public class MatchService {
    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Transactional
    public Match save(Match match) {
        return matchRepository.save(match);
    }

    @Transactional
    public Optional<Match> findById(Long id) {
        return matchRepository.findById(id);
    }

    @Transactional
    public List<Match> findAll() {
        return (List<Match>) matchRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        matchRepository.deleteById(id);
    }
}
