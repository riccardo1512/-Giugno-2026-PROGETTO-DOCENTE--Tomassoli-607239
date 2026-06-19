package it.uniroma3.siw.calcio.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.calcio.model.Team;
import it.uniroma3.siw.calcio.repository.TeamRepository;

@Component
public class TeamValidator implements Validator {

    private final TeamRepository teamRepository;

    public TeamValidator(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Team team = (Team) o;
        if (team.getName() != null && team.getYearOfFoundation() != 0) {
            // Se esiste già una squadra con lo stesso nome e anno di fondazione
            if (teamRepository.existsByNameAndYearOfFoundation(team.getName(), team.getYearOfFoundation())) {
                errors.reject("team.duplicate");
            }
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Team.class.equals(aClass);
    }
}
