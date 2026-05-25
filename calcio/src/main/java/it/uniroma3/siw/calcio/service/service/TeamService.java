package it.uniroma3.siw.movie.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.calcio.exception.DuplicateTeamException;
import it.uniroma3.siw.calcio.model.Team;
import it.uniroma3.siw.calcio.repository.TeamRepository;

@Service
public class TeamService {

   private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> findAll() {
        return this.teamRepository.findAll();
    }

    public Optional<Team> findById(Long id) {
        return this.teamRepository.findById(id);
    }

    @Transactional
    public Team save(Team team) throws DuplicateTeamException {
        boolean duplicate = team.getId() == null
            ? this.teamRepository.existsByNameAndYearOfFoundation(team.getName(), team.getYearOfFoundation())
            : this.teamRepository.existsByNameAndYearOfFoundationAndIdNot(team.getName(), team.getYearOfFoundation(), team.getId());
        if (duplicate) {
            throw new DuplicateTeamException(team.getName(), team.getYearOfFoundation());
        }
        return teamRepository.save(team);
    }

    @Transactional
    public void deleteById(Long id) {
        teamRepository.deleteById(id);
    }
}
