package it.uniroma3.siw.calcio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.calcio.model.Comment;
import it.uniroma3.siw.calcio.model.Match;
import it.uniroma3.siw.calcio.repository.MatchRepository;

@Service
public class MatchService {
    private final MatchRepository matchRepository;
    private final CommentService commentService;

    public MatchService(MatchRepository matchRepository, CommentService commentService) {
        this.matchRepository = matchRepository;
        this.commentService = commentService;
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
        Optional<Match> matchOpt = matchRepository.findById(id);
        if (matchOpt.isPresent()) {
            List<Comment> comments = commentService.findByMatch(matchOpt.get());
            for (Comment comment : comments) {
                commentService.delete(comment);
            }
            matchRepository.deleteById(id);
        }
    }
}
