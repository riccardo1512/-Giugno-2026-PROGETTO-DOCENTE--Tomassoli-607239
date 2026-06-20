package it.uniroma3.siw.calcio.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.calcio.model.Match;

@Component
public class MatchValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Match.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Match match = (Match) target;

        if (match.getTeamHome() != null && match.getTeamAway() != null) {
            if (match.getTeamHome().equals(match.getTeamAway())) {
                errors.rejectValue("teamAway", "same_team", "Una squadra non può giocare contro sé stessa");
            }
        }
    }
}
